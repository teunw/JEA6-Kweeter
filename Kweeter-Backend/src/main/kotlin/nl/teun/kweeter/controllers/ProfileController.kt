package nl.teun.kweeter.controllers

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.services.ValidatorService
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path("/profiles")
class ProfileController {

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var validatorService: ValidatorService

    @GET
    @Path("/{userId}")
    fun getProfile(@PathParam("userId") userId: String): Response? {
        if (userId.isBlank()) {
            return Response.serverError().entity("userId is empty").build()
        }
        val intToNumber = userId.toLongOrNull() ?: return Response.serverError().entity("userId is not parsable to long").build()
        val profile = profileService.findById(intToNumber)

        return Response.ok(profile).build()
    }

    @GET
    @Path("/")
    fun getProfiles(@QueryParam("results") results: Int, @QueryParam("page") page: Int): Response? {
        if (results > 100) {
            return Response.serverError().entity("For performance reasons > 100 results is not allowed").build()
        }
        val amountOfResults = if (results == 0) results else 50
        return Response.ok(profileService.findAll(amountOfResults, page)).build()
    }

    @PUT
    @Path("/{profileId}")
    fun updateProfile(
            @PathParam("profileId") id: Long,
            @DefaultValue("") @QueryParam("email") email: String,
            @DefaultValue("") @QueryParam("username") username: String,
            @DefaultValue("") @QueryParam("displayName") displayName: String
    ): Response {
        if (id < 0) {
            return Response.serverError().entity("Id cannot be blank, got \"$id\"").build()
        }
        val profile = profileService.findById(id)
        var updatedAnything = false

        if (!email.isBlank()) {
            profile.email = email
            updatedAnything = true
        }
        if (!username.isBlank()) {
            profile.username = username
            updatedAnything = true
        }
        if (!displayName.isBlank()) {
            profile.displayName = displayName
            updatedAnything = true
        }

        if (!updatedAnything) {
            return Response
                    .serverError()
                    .entity("One of the parameters is empty")
                    .entity(email)
                    .entity(username)
                    .entity(displayName)
                    .build()
        }
        this.profileService.updateProfile(profile)
        return Response.ok(profile).build()
    }

    @POST
    @Path("/create")
    @Produces
    fun createProfile(
            @QueryParam("email") email: String,
            @QueryParam("username") username: String,
            @QueryParam("displayName") displayName: String,
            @QueryParam("password") password: String
    ): Response? {
        if (email.isBlank() || username.isBlank() || displayName.isBlank() || password.isBlank()) {
            return Response
                    .serverError()
                    .entity("One of the parameters is empty")
                    .entity(email)
                    .entity(username)
                    .entity(displayName)
                    .entity(password)
                    .build()
        }
        if (!this.validatorService.isUsernameValid(username)) {
            val usernameRegex = this.validatorService.usernameRegex.pattern
            return Response.serverError().entity("Username is invalid, regex: $usernameRegex").build()
        }

        val profile = Profile()
        profile.setPassword(password)
        profile.email = email
        profile.username = username
        profile.displayName = displayName
        this.profileService.createProfile(profile)
        return Response.ok(profile).build()
    }
}