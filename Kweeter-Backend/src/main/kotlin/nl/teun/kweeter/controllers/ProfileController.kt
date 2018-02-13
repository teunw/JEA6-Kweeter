package nl.teun.kweeter.controllers

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.domain.ProfileAuth
import nl.teun.kweeter.services.ProfileAuthService
import nl.teun.kweeter.services.ProfileService
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path("/profiles")
class ProfileController {

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var profileAuthService: ProfileAuthService

    @POST
    @Path("/auth")
    fun postAuth(@QueryParam("id") userId: Long, @QueryParam("password") password: String): Response? {
        if (password.isBlank()) {
            return Response.serverError().entity("Password is empty").build()
        }
        val profile = this.profileService.findById(userId)
        val result = profile.checkPassword(password)

        if (!result) {
            return Response.serverError().entity("Invalid password").build()
        }
        val auth = ProfileAuth()
        auth.generateNewToken()
        auth.profile = profile

        this.profileAuthService.create(auth)
        return Response.ok(auth).build()
    }

    @GET
    @Path("/")
    fun getProfiles(@DefaultValue("") @QueryParam("id") userId: String): Response? {
        if (userId.isBlank()) {
            return Response.ok(profileService.findAll()).build()
        }
        val intToNumber = userId.toLongOrNull() ?: throw Exception("Could not parse int parameters")
        val profile = profileService.findById(intToNumber)
        return Response.ok(Arrays.asList(profile)).build()
    }

    @PUT
    @Path("/")
    fun updateProfile(
            @QueryParam("id") id: Long,
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
    @Path("/")
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
        val profile = Profile()
        profile.setPassword(password)
        profile.email = email
        profile.username = username
        profile.displayName = displayName
        this.profileService.createProfile(profile)
        return Response.ok(profile).build()
    }
}