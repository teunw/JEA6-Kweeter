package nl.teun.kweeter.filters

import nl.teun.kweeter.annotations.KweeterAuthRequired
import nl.teun.kweeter.services.AuthService
import javax.annotation.Priority
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
    private lateinit var authService: AuthService

    override fun filter(requestContext: ContainerRequestContext) {
        val authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        if (authHeader == null || !authHeader.trim().toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase())) {
            requestContext.abortWith(
                    Response
                            .status(Response.Status.BAD_REQUEST)
                            .entity("Auth header not valid or not present")
                            .build()
            )
            return
        }

        val token = authHeader.substring(AUTHENTICATION_SCHEME.length).trim()
        try {
            authService.findAuthToken(token)
        } catch (e: EntityNotFoundException) {
            requestContext.abortWith(
                    Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity("Auth token is invalid")
                            .build()
            )
            return
        }

    }
}