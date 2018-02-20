package nl.teun.kweeter.controllers

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Response

@Path("/kweets")
class KweetController {

    @Inject
    private lateinit var kweetService: KweetService

    @Inject
    private lateinit var profileService: ProfileService

    @GET
    @Path("/")
    @Produces
    fun getKweets(): List<Kweet> {
        return kweetService.findAll()
    }

    @GET
    @Path("/{kweetId}")
    @Produces
    fun getKweet(@PathParam("kweetId") kweetId : String): Response {
        if (kweetId.isEmpty()) {
            return Response.serverError().entity("kweetId is empty").build()
        }
        val kweetIdAsLong = kweetId.toLongOrNull() ?: return Response.serverError().entity("profileId not a long").build()

        return Response.ok(kweetService.findById(kweetIdAsLong)).build()
    }

    @PUT
    @Path("/{kweetId}")
    fun updateKweet(
            @PathParam("kweetId") kweetId: Long,
            @QueryParam("profileId") profileId: String,
            @QueryParam("textContent") textContent: String
    ): Response {
        if (profileId.isEmpty()) {
            return Response.serverError().entity("profileId is empty").build()
        }
        if (textContent.isEmpty()) {
            return Response.serverError().entity("textContent is empty").build()
        }
        val profileIdAsLong = profileId.toLongOrNull() ?: return Response.serverError().entity("profileId not a long").build()

        val kweet = this.kweetService.findById(kweetId)
        kweet.author = this.profileService.findById(profileIdAsLong)
        kweet.textContent = textContent
        return Response.ok(kweet).build()
    }

    @POST
    @Path("/create")
    fun createKweet(
            @QueryParam("profileId") profileId: String,
            @QueryParam("textContent") textContent: String
    ): Response {
        if (profileId.isEmpty()) {
            return Response.serverError().entity("profileId is empty").build()
        }
        if (textContent.isEmpty()) {
            return Response.serverError().entity("textContent is empty").build()
        }
        val profileIdAsLong = profileId.toLongOrNull() ?: return Response.serverError().entity("profileId not a long").build()

        val kweet = Kweet()
        kweet.author = this.profileService.findById(profileIdAsLong)
        kweet.textContent = textContent
        kweet.setPublicId(UUID.randomUUID())
        kweet.date = Date()
        this.kweetService.createKweet(kweet)

        return Response.ok(kweet).build()
    }

}