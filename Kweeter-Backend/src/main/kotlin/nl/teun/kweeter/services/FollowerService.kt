package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile

interface FollowerService {

    fun addFollower(target: Profile, follower: Profile)

    fun removeFollower(target: Profile, follower: Profile)

}