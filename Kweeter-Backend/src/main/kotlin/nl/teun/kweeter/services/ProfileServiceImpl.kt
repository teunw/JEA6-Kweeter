package nl.teun.kweeter.services

import nl.teun.kweeter.authentication.ProfileRole
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.services.search.ElasticKweeterSearchService
import nl.teun.kweeter.toProfileFacade
import java.security.Principal
import javax.ejb.Stateless
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException
import javax.persistence.PersistenceContext

@Stateless
class ProfileServiceImpl : ProfileService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Inject
    private lateinit var searchService: ElasticKweeterSearchService

    override fun recreateFromFacade(profileFacade: ProfileFacade): Profile {
        val profile = this.findById(profileFacade.id)
        profile.username = profileFacade.username
        profile.displayName = profileFacade.displayName
        profile.email = profileFacade.emailAddress
        profile.location = profileFacade.location
        profile.bio = profileFacade.bio
        profile.contactLink = profileFacade.contactLink
        if (profileFacade.role != null) profile.role = ProfileRole.valueOf(profileFacade.role)
        return profile
    }

    override fun findAll(maxResults: Int, offsetResults: Int) =
            this.entityManager
                    .createNamedQuery("Profile.all")
                    .setMaxResults(maxResults)
                    .setFirstResult(offsetResults)
                    .resultList
                    .filterIsInstance<Profile>()
                    .toList()

    override fun findById(id: Long) = entityManager.find(Profile::class.java, id)
            ?: throw EntityNotFoundException("Profile is null")

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

    override fun findByUsername(username: String): Profile {
        val results = this.entityManager
                .createNamedQuery("Profile.findbyusername")
                .setParameter("p_username", username)
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
        this.searchService.updateProfileIndex(profile.toProfileFacade())
    }

    override fun createProfile(profile: Profile) {
        this.entityManager.persist(profile)
        this.entityManager.flush()
        this.searchService.addProfileToIndex(profile.toProfileFacade())
    }

    override fun findByPrincipal(userPrincipal: Principal): Profile {
        val possibleProfiles = this.entityManager
                .createNamedQuery("Profile.findbyusername")
                .setParameter("p_username", userPrincipal.name)
                .resultList
                .filterIsInstance<Profile>()
        if (possibleProfiles.isEmpty()) {
            throw EntityNotFoundException("Profile not found")
        }
        if (possibleProfiles.size != 1) {
            throw Exception("Found multiple profiles for same username")
        }
        return possibleProfiles.first()
    }
}