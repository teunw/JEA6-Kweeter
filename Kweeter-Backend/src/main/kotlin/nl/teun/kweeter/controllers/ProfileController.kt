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
    @Path("/by-email/{email}")
    fun getProfileByEmail(@PathParam("email") email: String): Response? {
        if (email.isBlank()) {
            return Response.serverError().entity("Email cannot be blank").build()
        }
        val profile = this.profileService.findByEmail(email)
        return Response.ok(profile).build()
    }

    @GET
    @Path("/{userId}")
    fun getProfile(@PathParam("userId") userId: String): Response? {
        if (userId.isBlank()) {
            return Response.serverError().entity("userId is empty").build()
        }
        val intToNumber = userId.toLongOrNull()
                ?: return Response.serverError().entity("userId is not parsable to long").build()
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
            reqProfile: Profile
    ): Response {
        if (id < 0) {
            return Response.serverError().entity("Id cannot be blank, got \"$id\"").build()
        }
        val dbProfile = profileService.findById(id)
        var updatedAnything = false

        if (!reqProfile.email.isBlank()) {
            dbProfile.email = reqProfile.email
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

        if (!updatedAnything) {
            return Response
                    .serverError()
                    .entity("One of the parameters is empty")
                    .entity(reqProfile.email)
                    .entity(reqProfile.username)
                    .entity(reqProfile.displayName)
                    .build()
        }
        this.profileService.updateProfile(dbProfile)
        return Response.ok(dbProfile).build()
    }

    @POST
    @Path("/")
    fun createProfile(
            profile: Profile
    ): Response? {
        if (profile.email.isBlank() || profile.username.isBlank() || profile.displayName.isBlank()) {
            return Response
                    .serverError()
                    .entity("One of the parameters is empty")
                    .entity(profile.email)
                    .entity(profile.username)
                    .entity(profile.displayName)
                    .build()
        }
        if (!this.validatorService.isUsernameValid(profile.username)) {
            val usernameRegex = this.validatorService.usernameRegex.pattern
            return Response.serverError().entity("Username is invalid, regex: $usernameRegex").build()
        }
        this.profileService.createProfile(profile)
        return Response.ok(profile).build()
    }
}