package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.domain.ProfileFollower
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class FollowerServiceImpl : FollowerService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun addFollower(profileToBeFollowed: Profile, follower: Profile): ProfileFollower {
        val profileFollowers = this
                .entityManager
                .createNamedQuery("profilefollower.byprofile")
                .setParameter("profileId", profileToBeFollowed.id)
                .resultList
                .filterIsInstance<ProfileFollower>()

        val profileFollower: ProfileFollower
        if (profileFollowers.isEmpty()) {
            profileFollower = ProfileFollower()
            profileFollower.profile = profileToBeFollowed
        } else {
            profileFollower = profileFollowers.first()
        }

        if (profileFollower.followingProfiles.contains(follower)) {
            throw Exception("This profile already has @${follower.username} as follower")
        }
        profileFollower.followingProfiles.add(follower)

        if (profileFollower.id > 0) {
            entityManager.merge(profileFollower)
        } else {
            entityManager.persist(profileFollower)
        }
        return profileFollower
    }

    override fun removeFollower(profileToBeUnfollowed: Profile, follower: Profile): ProfileFollower {
        val profileFollowers = this
                .entityManager
                .createNamedQuery("profilefollower.byprofile")
                .setParameter("profileId", profileToBeUnfollowed.id)
                .resultList
                .filterIsInstance<ProfileFollower>()
        if (profileFollowers.size != 1) {
            throw Exception("Invalid number of followers (${profileFollowers.size}")
        }
        val profileFollower = profileFollowers.first()

        if (!profileFollower.followingProfiles.contains(follower)) {
            throw Exception("This profile doesnt have @${follower.username} as follower")
        }
        profileFollower.followingProfiles.remove(profileToBeUnfollowed)
        entityManager.merge(profileFollower)
        return profileFollower
    }

    override fun getFollowers(profile: Profile): ProfileFollower {
        val followers = this.entityManager
                .createNamedQuery("profilefollower.byprofile")
                .setParameter("profileId", profile.id)
                .resultList
                .filterIsInstance<ProfileFollower>()
        if (followers.isEmpty()) {
            return ProfileFollower(profile = profile)
        }
        return followers.first()
    }
}