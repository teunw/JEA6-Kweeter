package nl.teun.kweeter.domain

import javax.persistence.*

@Entity
@NamedQueries(
        NamedQuery(name = "profilefollower.byprofile", query = "SELECT pf FROM ProfileFollower pf WHERE pf.profile.id = :profileId")
)
data class ProfileFollower(
        @Id
        @GeneratedValue
        val id: Long = -1L,
        @Column(unique = true)
        var profile: Profile? = null,
        @OneToMany
        val followingProfiles: MutableList<Profile> = mutableListOf()
)