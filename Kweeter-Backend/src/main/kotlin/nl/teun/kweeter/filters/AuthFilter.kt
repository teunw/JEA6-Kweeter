package nl.teun.kweeter.filters

import nl.teun.kweeter.services.AuthService
import javax.inject.Inject
import javax.ws.rs.NotFoundException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

class AuthFilter : ContainerRequestFilter {

    private val AUTHENTICATION_SCHEME = "Bearer"

    @Inject
    private lateinit var authService: AuthService

    override fun filter(requestContext: ContainerRequestContext) {
        val authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        if (authHeader == null || authHeader.trim().toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase())) {
            requestContext.abortWith(
                    Response
                            .status(Response.Status.BAD_REQUEST)
                            .entity("Auth header not valid or present")
                            .build()
            )
        }

        val token = authHeader.substring(AUTHENTICATION_SCHEME.length).trim()
        try {
            this.authService.findAuthToken(token)
        } catch (e: NotFoundException) {
            requestContext.abortWith(
                    Response
                            .status(Response.Status.UNAUTHORIZED)
                            .entity("Auth token is invalid")
                            .build()
            )
        }

    }
}