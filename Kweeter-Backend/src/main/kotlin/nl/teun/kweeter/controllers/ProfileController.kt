package nl.teun.kweeter.controllers

import nl.teun.kweeter.authentication.annotations.KweeterAuthRequired
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.httpResponseBadRequest
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.services.ValidatorService
import nl.teun.kweeter.toProfileFacade
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/profiles")
class ProfileController {

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var validatorService: ValidatorService

    @GET
    @Path("/by-email/{email}")
    fun getProfileByEmail(@PathParam("email") email: String): Response? {
        if (email.isBlank()) {
            return httpResponseBadRequest().entity("Email cannot be blank").build()
        }
        val profile = this.profileService.findByEmail(email)
        return Response.ok(profile.toProfileFacade()).build()
    }

    @GET
    @Path("/{userId}")
    fun getProfile(@PathParam("userId") userId: String): Response? {
        if (userId.isBlank()) {
            return httpResponseBadRequest().entity("userId is empty ($userId)").build()
        }
        val intToNumber = userId.toLongOrNull()
                ?: return Response.serverError().entity("userId is not parsable to long").build()
        val profile = profileService.findById(intToNumber)

        return Response.ok(profile.toProfileFacade()).build()
    }

    @GET
    @Path("/")
    fun getProfiles(@QueryParam("results") results: Int, @QueryParam("page") page: Int): Response? {
        if (results > 100) {
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(profileService.findAll(100 )).build()
        }
        val amountOfResults = if (results == 0) results else 50
        val profileFacades = this.profileService.findAll(amountOfResults, page).map { it.toProfileFacade() }
        return Response.ok(profileFacades).build()
    }

    @PUT
    @KweeterAuthRequired
    @Path("/")
    fun updateProfile(
            reqProfile: ProfileFacade,
            @Context securityContext: SecurityContext
    ): Response {
        val dbProfile = profileService.findByPrincipal(securityContext.userPrincipal)
        var updatedAnything = false

        if (!reqProfile.emailAddress.isBlank()) {
            dbProfile.email = reqProfile.emailAddress
            updatedAnything = true
        }
        if (!reqProfile.username.isBlank()) {
            dbProfile.username = reqProfile.username
            updatedAnything = true
        }
        if (!reqProfile.displayName.isBlank()) {
            dbProfile.displayName = reqProfile.displayName
            updatedAnything = true
        }
        if (!reqProfile.bio.isBlank()) {
            dbProfile.bio = reqProfile.bio
            updatedAnything = true
        }
        if (!reqProfile.location.isBlank()) {
            dbProfile.location = reqProfile.location
            updatedAnything = true
        }

        if (!updatedAnything) {
            return httpResponseBadRequest()
                    .entity("One of the parameters is empty")
                    .entity(reqProfile.emailAddress)
                    .entity(reqProfile.username)
                    .entity(reqProfile.displayName)
                    .build()
        }
        this.profileService.updateProfile(dbProfile)
        return Response.ok(dbProfile.toProfileFacade()).build()
    }

    @POST
    @Path("/")
    fun createProfile(
            profileFacade: ProfileFacade
    ): Response? {
        if (profileFacade.emailAddress.isBlank() || profileFacade.username.isBlank() || profileFacade.displayName.isBlank()) {
            return httpResponseBadRequest()
                    .entity("One of the parameters is empty")
                    .entity(profileFacade.emailAddress)
                    .entity(profileFacade.username)
                    .entity(profileFacade.displayName)
                    .build()
        }
        if (!this.validatorService.isUsernameValid(profileFacade.username)) {
            return Response.serverError().entity("Username is invalid").build()
        }

        val profile = this.profileService.recreateFromFacade(profileFacade)
        this.profileService.createProfile(profile)
        return Response.ok(profile.toProfileFacade()).build()
    }
}