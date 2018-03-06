package nl.teun.kweeter.controllers

import nl.teun.kweeter.controllers.types.request.AuthenticationRequest
import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.services.AuthService
import nl.teun.kweeter.services.ProfileService
import javax.inject.Inject
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@Path("/auth")
class AuthenticationController {

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var authenticationService: AuthService

    @POST
    @Path("/authenticate/{profileId}")
    fun authenticateUser(@PathParam("profileId") profileId: Long, authRequest: AuthenticationRequest): Response {
        val profile = profileService.findById(profileId)

        if (profile.checkPassword(authRequest.password)) {
            return Response.status(Response.Status.FORBIDDEN).build()
        }

        val authToken = AuthToken(profile = profile)
        this.authenticationService.insertAuthToken(authToken)

        return Response.ok(authToken).build()
    }

}