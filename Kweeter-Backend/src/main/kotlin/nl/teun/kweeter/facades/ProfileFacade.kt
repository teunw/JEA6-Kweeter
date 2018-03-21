package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.Profile

data class ProfileFacade(private val profile: Profile = Profile()) {
    val id: Long = profile.id
    val username: String = profile.username
    val emailAddress: String = profile.email
    val bio: String? = profile.bio
    val contactLink: String? = profile.contactLink
    val displayName: String? = profile.displayName
    val location: String? = profile.location
    val role = profile.role?.toString()
}