package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.domain.ProfileFollower

interface FollowerService {

    fun addFollower(target: Profile, follower: Profile)

    fun removeFollower(target: Profile, follower: Profile)

    fun getFollowers(target: Profile): List<ProfileFollower>
}