package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.facades.UnsafeProfileFacade
import java.security.Principal
import javax.persistence.EntityNotFoundException

interface ProfileService {

    fun findAll(maxResults: Int = 50, offsetResults: Int = 0): List<Profile>

    @Throws(EntityNotFoundException::class)
    fun findById(id: Long): Profile

    @Throws(EntityNotFoundException::class)
    fun findByEmail(email: String): Profile

    fun findByUsername(username: String): Profile

    fun updateProfile(profile: Profile)

    fun createProfile(profile: Profile)

    fun findByPrincipal(userPrincipal: Principal): Profile

    fun recreateFromFacade(profileFacade: ProfileFacade): Profile

    fun recreateFromFacade(profileFacade: UnsafeProfileFacade, fromDb: Boolean = false): Profile

}