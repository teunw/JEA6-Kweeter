package nl.teun.kweeter.controllers

import nl.teun.kweeter.authentication.annotations.KweeterAuthRequired
import nl.teun.kweeter.facades.UnsafeProfileFacade
import nl.teun.kweeter.httpResponseBadRequest
import nl.teun.kweeter.services.EmailService
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.services.ValidatorService
import nl.teun.kweeter.toProfileRestFacade
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

    @Inject
    private lateinit var emailService: EmailService

    @GET
    @Path("/by-email/{email}")
    fun getProfileByEmail(@PathParam("email") email: String): Response? {
        if (email.isBlank()) {
            return httpResponseBadRequest().entity("Email cannot be blank").build()
        }
        val profile = this.profileService.findByEmail(email)
        return Response.ok(profile.toProfileRestFacade()).build()
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

        return Response.ok(profile.toProfileRestFacade()).build()
    }

    @GET
    @Path("/")
    fun getProfiles(@QueryParam("results") results: Int, @QueryParam("page") page: Int): Response? {
        if (results > 100) {
            return Response.status(Response.Status.PARTIAL_CONTENT).entity(profileService.findAll(100 )).build()
        }
        val amountOfResults = if (results == 0) results else 50
        val profileFacades = this.profileService.findAll(amountOfResults, page).map { it.toProfileRestFacade() }
        return Response.ok(profileFacades).build()
    }

    @PUT
    @KweeterAuthRequired
    @Path("/")
    fun updateProfile(
            reqProfile: UnsafeProfileFacade,
            @Context securityContext: SecurityContext
    ): Response {
        val dbProfile = profileService.findByPrincipal(securityContext.userPrincipal)
        var updatedAnything = false

        if (reqProfile.email != null && !reqProfile.email.isBlank()) {
            dbProfile.email = reqProfile.email
            updatedAnything = true
        }
        if (reqProfile.username != null && !reqProfile.username.isBlank()) {
            dbProfile.username = reqProfile.username
            updatedAnything = true
        }
        if (reqProfile.displayName == null || !reqProfile.displayName.isBlank()) {
            dbProfile.displayName = reqProfile.displayName
            updatedAnything = true
        }
        if (reqProfile.bio == null || !reqProfile.bio.isBlank()) {
            dbProfile.bio = reqProfile.bio
            updatedAnything = true
        }
        if (reqProfile.location == null || !reqProfile.location.isBlank()) {
            dbProfile.location = reqProfile.location
            updatedAnything = true
        }

        if (!updatedAnything) {
            return httpResponseBadRequest()
                    .entity("One of the parameters is empty")
                    .entity(reqProfile.email)
                    .entity(reqProfile.username)
                    .entity(reqProfile.displayName)
                    .build()
        }
        this.profileService.updateProfile(dbProfile)
        return Response.ok(dbProfile.toProfileRestFacade()).build()
    }

    @POST
    @Path("/")
    fun createProfile(
            profileFacade: UnsafeProfileFacade
    ): Response? {
        if (profileFacade.email == null || profileFacade.email.isBlank() || profileFacade.username == null || profileFacade.username.isBlank() || profileFacade.displayName!!.isBlank()) {
            return httpResponseBadRequest()
                    .entity("One of the parameters is empty")
                    .entity(profileFacade.email)
                    .entity(profileFacade.username)
                    .entity(profileFacade.displayName)
                    .build()
        }
        if (profileFacade.username == null || !this.validatorService.isUsernameValid(profileFacade.username)) {
            return Response.serverError().entity("Username is invalid").build()
        }


        val profile = this.profileService.recreateFromFacade(profileFacade, fromDb = false)
        this.profileService.createProfile(profile)
        this.emailService.sendMail(to = profileFacade.email, subject = "Account created!", text = "Take a look: http://localhost:4200")
        return Response.ok(profile.toProfileRestFacade()).build()
    }
}