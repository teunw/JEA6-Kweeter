package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import java.util.*
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class KweetServiceImpl : KweetService {

    @PersistenceContext
    private lateinit var entityManager : EntityManager

    override fun findAll(maxResults: Int, offsetResults: Int): List<Kweet> {
        return this.entityManager
                .createNamedQuery("Kweet.all")
                .setMaxResults(maxResults)
                .setFirstResult(offsetResults)
                .resultList
                .filterIsInstance<Kweet>()
                .toList()
    }

    override fun findById(id : Long): Kweet {
        val returnList = this.entityManager
                .createNamedQuery("Kweet.findbyid")
                .setParameter("k_id", id)
                .resultList
                .filterIsInstance<Kweet>()
        return returnList.first()
    }

    override fun findByProfile(profile : Profile) : List<Kweet> {
        return this.entityManager
                .createNamedQuery("Kweet.findbyprofile")
                .setParameter("p_id", profile.id)
                .resultList
                .filterIsInstance<Kweet>()
    }

    override fun updateKweet(kweet: Kweet) {
        this.entityManager.merge(kweet)
    }

    override fun createKweet(kweet: Kweet) {
        if (kweet.publicId == null) {
            kweet.setPublicId(UUID.randomUUID())
        }
        this.entityManager.persist(kweet)
    }
}