package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException
import javax.persistence.PersistenceContext

@Stateless
class ProfileServiceImpl : ProfileService {

    @PersistenceContext
    private lateinit var entityManager : EntityManager

    override fun findAll(maxResults: Int, offsetResults: Int): List<Profile> {
        return this.entityManager
                .createNamedQuery("Profile.all")
                .setMaxResults(maxResults)
                .setFirstResult(offsetResults)
                .resultList
                .filterIsInstance<Profile>()
                .toList()
    }

    override fun findById(id : Long): Profile {
        return entityManager.find(Profile::class.java, id) ?: throw EntityNotFoundException("Profile is null")
    }

    override fun findByEmail(email: String): Profile {
        val results = this.entityManager
                .createNamedQuery("Profile.findbyemail")
                .setParameter("p_email", email)
                .resultList
                .filterIsInstance<Profile>()
        if (results.isEmpty()) {
            throw EntityNotFoundException("Profile not found")
        }
        return results.first()
    }

    override fun updateProfile(profile: Profile) {
        this.entityManager.merge(profile)
        this.entityManager.flush()
    }

    override fun createProfile(profile: Profile) {
        this.entityManager.persist(profile)
        this.entityManager.flush()
    }
}