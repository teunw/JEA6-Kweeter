package nl.teun.kweeter.authentication

import nl.teun.kweeter.domain.AuthToken
import javax.ws.rs.core.SecurityContext

class KweetUserSecurityContext(
        val authToken: AuthToken
) : SecurityContext {
    override fun isUserInRole(role: String?): Boolean {
        val profile = this.authToken.profile ?: return false
        return profile.hasRole(role)
    }

    override fun getAuthenticationScheme() = "JWT"

    override fun getUserPrincipal() = ProfileSecurityPrincipal(this.authToken.profile!!)

    override fun isSecure() = this.authToken.experationDate.isBeforeNow
}