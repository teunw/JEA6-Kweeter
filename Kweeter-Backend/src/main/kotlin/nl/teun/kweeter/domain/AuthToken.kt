package nl.teun.kweeter.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@NamedQueries(
        NamedQuery(name = "authToken.forprofile", query = "SELECT at FROM AuthToken at WHERE at.profile.id = :profileId"),
        NamedQuery(name = "authToken.fortoken", query = "SELECT at FROM AuthToken at WHERE at.token = :token")
)
data class AuthToken(
        @Id
        @GeneratedValue
        val id: Long = -1,
        @Column(unique = false)
        val token: String? = null,
        @OneToOne
        val profile: Profile? = null,
        @Column
        val issueDate: LocalDateTime = LocalDateTime.now(),
        @Column
        val experationDate: LocalDateTime = LocalDateTime.now().plusYears(1)
)