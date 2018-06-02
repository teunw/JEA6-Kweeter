package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.toJavaUtilDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.ws.rs.NotFoundException

class KweetServiceImpl : KweetService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    constructor()

    constructor(entityManager: EntityManager? = null) {
        if (entityManager != null) {
            this.entityManager = entityManager
        }
    }

    override fun findByPublicId(id: String): Kweet {
        val kweets = this
                .entityManager
                .createNamedQuery("Kweet.findbypublicId")
                .setParameter("k_publicId", id)
                .resultList
                .filterIsInstance<Kweet>()
        if (kweets.isEmpty()) {
            throw NotFoundException("No instances found")
        } else if (kweets.size > 1) {
            throw Exception("Invalid number of instances found")
        }
        return kweets.first()
    }

    override fun findAll(maxResults: Int, offsetResults: Int) =
            this.entityManager
                    .createNamedQuery("Kweet.all")
                    .setMaxResults(maxResults)
                    .setFirstResult(offsetResults)
                    .resultList
                    .filterIsInstance<Kweet>()
                    .sortedByDescending { it.date.toJavaUtilDate().time }

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

    override fun deleteKweet(kweet: Kweet) {
        val kweetToRemove = if (this.entityManager.contains(kweet)) kweet else this.entityManager.merge(kweet)
        this.entityManager.remove(kweetToRemove)
    }

    override fun getKweetsAfter(dateTime: LocalDateTime) =
            this.entityManager
                    .createNamedQuery("kweet.findafter")
                    .setParameter("k_date", dateTime)
                    .resultList
                    .filterIsInstance<Kweet>()
}
