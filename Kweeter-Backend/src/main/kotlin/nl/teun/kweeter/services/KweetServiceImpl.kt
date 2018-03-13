package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.KweetFacade
import java.util.*
import javax.ejb.Stateless
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.ws.rs.NotFoundException

@Stateless
class KweetServiceImpl : KweetService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Inject
    private lateinit var profileService: ProfileService

    override fun findAll(maxResults: Int, offsetResults: Int) =
            this.entityManager
                    .createNamedQuery("Kweet.all")
                    .setMaxResults(maxResults)
                    .setFirstResult(offsetResults)
                    .resultList
                    .filterIsInstance<Kweet>()
                    .sortedByDescending { it.date.time }

    override fun findById(id: Long) = this.entityManager.find(Kweet::class.java, id)
            ?: throw NotFoundException("Kweet not found")

    override fun findByProfile(profile: Profile) =
            this.entityManager
                    .createNamedQuery("Kweet.findbyprofile")
                    .setParameter("p_id", profile.id)
                    .resultList
                    .filterIsInstance<Kweet>()

    override fun updateKweet(kweet: Kweet) {
        this.entityManager.merge(kweet)
    }

    override fun createKweet(kweet: Kweet) {
        if (kweet.publicId == null) {
            kweet.setPublicId(UUID.randomUUID())
        }
        this.entityManager.persist(kweet)
    }

    override fun recreateFromFacade(kweetFacade: KweetFacade): Kweet {
        val kweet = this.findById(kweetFacade.id)

        kweet.textContent = kweetFacade.textContent
        kweet.author = this.profileService.recreateFromFacade(kweetFacade.author)
        kweet.setPublicId(UUID.fromString(kweetFacade.publicId))
        kweet.internalId = kweetFacade.id
        kweet.likedBy = kweetFacade.likedBy.map { this.profileService.findByUsername(it) }
        kweet.date = kweetFacade.date

        return kweet
    }
}