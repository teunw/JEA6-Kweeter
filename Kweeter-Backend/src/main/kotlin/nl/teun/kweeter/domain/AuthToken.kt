package nl.teun.kweeter.domain

import org.joda.time.DateTime
import java.util.*
import javax.persistence.*

@Entity
@NamedQueries(
        NamedQuery(name = "authToken.forprofile", query = "SELECT at FROM AuthToken at WHERE at.profile.id = :profileId"),
        NamedQuery(name = "authToken.fortoken", query = "SELECT at FROM AuthToken at WHERE at.token = :token")
)
data class AuthToken(
        @Id
        val token: String = UUID.randomUUID().toString(),
        @Column
        val profile: Profile? = null,
        @Column
        val issueDate: DateTime = DateTime.now(),
        @Column
        val experationDate: DateTime = DateTime.now().plusYears(1)
)