package nl.teun.kweeter.services

import nl.teun.kweeter.authentication.ProfileRole
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.facades.UnsafeProfileFacade
import java.security.Principal
import javax.persistence.EntityNotFoundException

abstract class ProfileService {

    abstract fun findAll(maxResults: Int = 50, offsetResults: Int = 0): List<Profile>

    @Throws(EntityNotFoundException::class)
    abstract fun findById(id: Long): Profile

    @Throws(EntityNotFoundException::class)
    abstract fun findByEmail(email: String): Profile

    abstract fun findByUsername(username: String): Profile

    abstract fun updateProfile(profile: Profile)

    abstract fun createProfile(profile: Profile)

    fun findByPrincipal(userPrincipal: Principal): Profile {
        return this.findByUsername(userPrincipal.name)
    }

    open fun recreateFromFacade(profileFacade: ProfileFacade): Profile {
        val profile = this.findById(profileFacade.id)
        profile.username = profileFacade.username
        profile.displayName = profileFacade.displayName
        profile.email = profileFacade.email
        profile.location = profileFacade.location
        profile.bio = profileFacade.bio
        profile.contactLink = profileFacade.contactLink
        if (profileFacade.role != null) profile.role = ProfileRole.valueOf(profileFacade.role)
        return profile
    }

    open fun recreateFromFacade(profileFacade: UnsafeProfileFacade, fromDb: Boolean): Profile {
        val profile = if (fromDb) this.findById(profileFacade.id!!) else Profile()
        profile.username = profileFacade.username
        profile.displayName = profileFacade.displayName
        profile.email = profileFacade.email
        profile.location = profileFacade.location
        profile.bio = profileFacade.bio
        profile.contactLink = profileFacade.contactLink
        if (profileFacade.password != null) profile.setPassword(profileFacade.password)
        if (profileFacade.role != null) profile.role = ProfileRole.valueOf(profileFacade.role)
        return profile
    }

}