package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.domain.ProfileFollower

interface FollowerService {

    fun addFollower(profileToBeFollowed: Profile, follower: Profile): ProfileFollower

    fun removeFollower(profileToBeUnfollowed: Profile, follower: Profile): ProfileFollower

    fun getFollowers(profile: Profile): ProfileFollower
}