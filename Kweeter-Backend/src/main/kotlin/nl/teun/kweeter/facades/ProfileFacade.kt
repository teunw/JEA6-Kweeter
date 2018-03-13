package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.Profile

data class ProfileFacade(val profile: Profile) {
    val id = profile.id
    val username = profile.username
    val emailAddress = profile.email
    val bio = profile.bio
    val contactLink = profile.contactLink
    val displayName = profile.displayName
    val role = profile.role.toString()
    val followers = profile.followers.map { it.followers = emptyList() }.filterIsInstance<Profile>()
}