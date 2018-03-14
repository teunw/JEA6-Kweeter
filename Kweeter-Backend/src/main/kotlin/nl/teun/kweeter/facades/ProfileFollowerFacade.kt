package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.ProfileFollower
import nl.teun.kweeter.toProfileFacade

class ProfileFollowerFacade(private val profileFollower: ProfileFollower = ProfileFollower()) {
    val id = profileFollower.id
    val profile = profileFollower.profile?.toProfileFacade()
    val followingProfiles = profileFollower.followingProfiles.map { it.toProfileFacade() }
}