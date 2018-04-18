package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.Profile

open class ProfileFacade(private val profile: Profile = Profile()) : UnsafeProfileFacade(profile) {
    override val id: Long = profile.id
}

open class UnsafeProfileFacade(private val profile: Profile? = null) {
    open val id: Long? = profile?.id
    val username: String? = profile?.username
    val emailAddress: String? = profile?.email
    val bio: String? = profile?.bio
    val contactLink: String? = profile?.contactLink
    val displayName: String? = profile?.displayName
    val location: String? = profile?.location
    val role = profile?.role?.toString()
    val password: String? = null
}