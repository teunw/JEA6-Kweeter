package nl.teun.kweeter.controllers

import nl.teun.kweeter.controllers.types.request.AuthenticationRequest
import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.filters.AuthFilter
import nl.teun.kweeter.httpResponseBadRequest
import nl.teun.kweeter.services.AuthService
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.toAuthFacade
import javax.inject.Inject
import javax.persistence.EntityNotFoundException
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

@Path("/auth")
class AuthenticationController {

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var authenticationService: AuthService

    @POST
    @Path("/authenticate")
    fun authenticateUser(authRequest: AuthenticationRequest): Response {
        if (authRequest.email == null || authRequest.email.isBlank() || authRequest.password == null || authRequest.password.isBlank()) {
            return httpResponseBadRequest().entity("Email or password field is blank").build()
        }

        val profile = profileService.findByEmail(authRequest.email)

        if (!profile.checkPassword(authRequest.password)) {
            return Response.status(Response.Status.FORBIDDEN).entity("Incorrect password ${authRequest.password}").build()
        }

        val authToken = AuthToken(profile = profile)
        this.authenticationService.insertAuthToken(authToken)

        return Response.ok(authToken).header(HttpHeaders.AUTHORIZATION, "Bearer ${authToken.token}").build()
    }

    @GET
    @Path("/validate")
    fun validateKey(@HeaderParam(HttpHeaders.AUTHORIZATION) authorization: String): Response {

        if (!authorization.trim().toLowerCase().startsWith(AuthFilter.AUTHENTICATION_SCHEME.toLowerCase())) {
            return httpResponseBadRequest()
                    .entity("Auth header not valid or not present")
                    .build()

        }

        val token = authorization.substring(AuthFilter.AUTHENTICATION_SCHEME.length).trim()
        return try {
            val authToken = this.authenticationService.findAuthToken(token)
            Response.ok(authToken.toAuthFacade()).build()
        } catch (e: EntityNotFoundException) {
            Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Auth token is invalid")
                    .build()
        }
    }
}