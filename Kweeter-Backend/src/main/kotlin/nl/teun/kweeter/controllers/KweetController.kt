package nl.teun.kweeter.controllers

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.ProfileService
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*

@Path("/kweet")
class KweetController {

    @Inject
    private lateinit var profileService : ProfileService

    @GET
    @Path("/all")
    @Produces
    fun getProfiles(): List<Profile> {
        return profileService.findAll()
    }

    @POST
    @Path("/create")
    fun createProfile(
            @QueryParam("email") email : String,
            @QueryParam("username") username : String,
            @QueryParam("displayName") displayName : String
    ) : Profile {
        if (email.isEmpty()) {
            throw Exception("Email adres is empty")
        }
        if (username.isEmpty()) {
            throw Exception("Username is empty")
        }
        if (displayName.isEmpty()) {
            throw Exception("Displayname is empty")
        }

        val profile = Profile()
        profile.email = email
        profile.username = username
        profile.displayName = displayName
        this.profileService.createProfile(profile)
        return profile
    }

}