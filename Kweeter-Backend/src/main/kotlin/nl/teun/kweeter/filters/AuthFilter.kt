package nl.teun.kweeter.filters

import nl.teun.kweeter.authentication.KweetUserSecurityContext
import nl.teun.kweeter.authentication.annotations.AuthenticatedUser
import nl.teun.kweeter.authentication.annotations.KweeterAuthRequired
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.httpResponseBadRequest
import nl.teun.kweeter.services.KeyService
import nl.teun.kweeter.services.ProfileService
import java.security.SignatureException
import javax.annotation.Priority
import javax.enterprise.event.Event
import javax.inject.Inject
import javax.persistence.EntityNotFoundException
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
@KweeterAuthRequired
@Priority(Priorities.AUTHENTICATION)
open class AuthFilter : ContainerRequestFilter {

    companion object {
        const val AUTHENTICATION_SCHEME = "Bearer"
    }

    @Inject
    @AuthenticatedUser
    private lateinit var userAuthenticatedEvent: Event<String>

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var keyService: KeyService

    override fun filter(requestContext: ContainerRequestContext) {
        val authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        if (authHeader == null || !authHeader.trim().toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase())) {
            requestContext.abortWith(
                    httpResponseBadRequest()
                            .entity("Auth header not valid or not present")
                            .build()
            )
            return
        }

        val compactJws = authHeader.substring(AUTHENTICATION_SCHEME.length).trim()

        val keySubject: String
        try {
            keySubject = this.keyService.getKeyAsString(compactJws)
        } catch (signatureException: SignatureException) {
            signatureException.printStackTrace()
            requestContext.abortWith(
                    Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity("Auth token is invalid")
                            .build()
            )
            return
        }

        val profile: Profile
        try {
            profile = profileService.findByEmail(keySubject)
        } catch (e: EntityNotFoundException) {
            requestContext.abortWith(
                    Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity("Auth token is invalid")
                            .build()
            )
            return
        }
        requestContext.securityContext = KweetUserSecurityContext(profile)
        this.userAuthenticatedEvent.fire(compactJws)
    }
}