package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class KweetServiceImpl : KweetService {

    @PersistenceContext
    private lateinit var entityManager : EntityManager

    override fun findAll(): List<Kweet> {
        return this.entityManager
                .createNamedQuery("Kweet.all")
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
        if (returnList.size != 1) {
            throw Exception("Found invalid number of kweets (should be 1): (${returnList.size}), for id $id")
        }
        return returnList.first()
    }

    override fun findByProfile(profile : Profile) : List<Kweet> {
        return this.entityManager
                .createNamedQuery("Kweet.findbyprofile")
                .setParameter("p_id", profile.id)
                .resultList
                .filterIsInstance<Kweet>()
    }

    override fun updateKweet(profile: Kweet) {
        this.entityManager.merge(profile)
    }

    override fun createKweet(profile: Kweet) {
        this.entityManager.persist(profile)
    }
}