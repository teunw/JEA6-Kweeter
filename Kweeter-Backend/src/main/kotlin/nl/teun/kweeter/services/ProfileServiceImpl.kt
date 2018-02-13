package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class ProfileServiceImpl : ProfileService {

    @PersistenceContext
    private lateinit var entityManager : EntityManager

    override fun findAll(): List<Profile> {
        return this.entityManager
                .createNamedQuery("Profile.all")
                .resultList
                .filterIsInstance<Profile>()
                .toList()
    }

    override fun findById(id : Long): Profile {
        val returnList = this.entityManager
                .createNamedQuery("Profile.findbyid")
                .setParameter("p_id", id)
                .resultList
                .filterIsInstance<Profile>()
        if (returnList.size != 1) {
            throw Exception("Found invalid number of profiles (should be 1): (${returnList.size}), for id $id")
        }
        return returnList.first()
    }

    override fun updateProfile(profile: Profile) {
        this.entityManager.merge(profile)
    }

    override fun createProfile(profile: Profile) {
        this.entityManager.persist(profile)
    }
}