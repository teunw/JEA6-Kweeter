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
        @OneToOne
        var profile: Profile? = null,
        @ManyToMany
        val followingProfiles: MutableList<Profile> = mutableListOf()
)