package nl.teun.kweeter.authentication

import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.domain.Profile
import java.time.LocalDateTime
import javax.ws.rs.core.SecurityContext

class KweetUserSecurityContext(
        private val profile:Profile
) : SecurityContext {
    override fun isUserInRole(role: String?) = this.profile.hasRole(role)

    override fun getAuthenticationScheme() = "JWT"

    override fun getUserPrincipal() = ProfileSecurityPrincipal(this.profile)

    override fun isSecure() = true

}