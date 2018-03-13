package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.Profile

data class ProfileFacade(private val profile: Profile = Profile()) {
    val id = profile.id
    val username = profile.username
    val emailAddress = profile.email
    val bio = profile.bio
    val contactLink = profile.contactLink
    val displayName = profile.displayName
    val location = profile.location
    val role = profile.role?.toString()
}