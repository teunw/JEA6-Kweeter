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

    override fun addFollower(target: Profile, follower: Profile) {
        val profileFollowers = this
                .entityManager
                .createNamedQuery("profilefollower.byprofile")
                .setParameter("profileId", target.id)
                .resultList
                .filterIsInstance<ProfileFollower>()

        val profileFollower: ProfileFollower
        if (profileFollowers.isEmpty()) {
            profileFollower = ProfileFollower()
            profileFollower.profile = target
        } else {
            profileFollower = profileFollowers[0]
        }

        if (profileFollower.followingProfiles.contains(follower)) {
            throw Exception("This profile already has @${follower.username} as follower")
        }
        profileFollower.followingProfiles.add(follower)

        if (profileFollower.id >= 1) {
            entityManager.merge(profileFollower)
        } else {
            entityManager.persist(profileFollower)
        }
    }

    override fun removeFollower(target: Profile, follower: Profile) {
        val profileFollowers = this
                .entityManager
                .createNamedQuery("profilefollower.byprofile")
                .setParameter("profileId", target.id)
                .resultList
                .filterIsInstance<ProfileFollower>()
        if (profileFollowers.size != 1) {
            throw Exception("Invalid number of followers (${profileFollowers.size}")
        }
        val profileFollower = profileFollowers[0]

        if (!profileFollower.followingProfiles.contains(follower)) {
            throw Exception("This profile doesnt have @${follower.username} as follower")
        }
        profileFollower.followingProfiles.remove(follower)
        entityManager.persist(profileFollower)
    }

    override fun getFollowers(target: Profile): List<ProfileFollower> =
            this.entityManager
            .createNamedQuery("profilefollower.byprofile")
            .setParameter("profileId", target.id)
            .resultList
            .filterIsInstance<ProfileFollower>()
}