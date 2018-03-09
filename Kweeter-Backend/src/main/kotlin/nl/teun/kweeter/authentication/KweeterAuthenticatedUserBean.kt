package nl.teun.kweeter.authentication

import nl.teun.kweeter.authentication.annotations.AuthenticatedUser
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.AuthService
import javax.enterprise.context.RequestScoped
import javax.enterprise.event.Observes
import javax.enterprise.inject.Produces
import javax.inject.Inject

@RequestScoped
class KweeterAuthenticatedUserBean {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    private var authenticatedUserId: Profile? = null

    @Inject
    private lateinit var authService: AuthService

    fun handleAuthenticationEvent(@Observes @AuthenticatedUser token: String) {
        // For some reason this only gets the profile id
        this.authenticatedUserId = this.authService.findAuthToken(token).profile
    }

}