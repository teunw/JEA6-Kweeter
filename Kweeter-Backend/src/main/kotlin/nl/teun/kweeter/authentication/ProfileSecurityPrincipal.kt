package nl.teun.kweeter.authentication

import nl.teun.kweeter.domain.Profile
import java.security.Principal

class ProfileSecurityPrincipal(
        val profile: Profile
) : Principal {
    override fun getName() = profile.username!!
}