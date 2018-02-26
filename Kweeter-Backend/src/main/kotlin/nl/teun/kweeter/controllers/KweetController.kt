package nl.teun.kweeter.controllers

import nl.teun.kweeter.controllers.requestTypes.KweetPost
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import java.time.LocalDateTime
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
    fun getKweets(@QueryParam("results") results: Int, @QueryParam("page") page: Int): Response? {
        if (results > 100) {
            return Response.serverError().entity("For performance reasons > 100 results is not allowed").build()
        }
        val amountOfResults = if (results != 0) results else 50
        return Response.ok(kweetService.findAll(amountOfResults, page * results)).build()
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

    @GET
    @Path("/forprofile/{profileId}")
    fun forProfile(@PathParam("profileId") profileId: String): Response {
        if (profileId.isBlank()) {
            return Response.serverError().entity("Profile with id: $profileId not found").build()
        }
        val longProfileId = profileId.toLongOrNull()
                ?: return Response.serverError().entity("Profile ID not a long").build()
        val profile = this.profileService.findById(longProfileId)
        val kweets = this.kweetService.findByProfile(profile)
        return Response.ok(kweets).build()
    }

    @POST
    @Path("/")
    fun createKweet(
            requestPost : KweetPost
    ): Response {
        if (requestPost.profileId.isEmpty()) {
            return Response.serverError().entity("profileId is empty (\"${requestPost.profileId}\")").build()
        }
        if (requestPost.textContent.isEmpty()) {
            return Response.serverError().entity("textContent is empty (\"${requestPost.textContent}\", \"${requestPost.profileId}\")").build()
        }
        val profileIdAsLong = requestPost.profileId.toLongOrNull() ?: return Response.serverError().entity("profileId not a long").build()

        val kweet = Kweet()
        kweet.author = this.profileService.findById(profileIdAsLong)
        kweet.textContent = requestPost.textContent
        kweet.setPublicId(UUID.randomUUID())
        kweet.setDate(LocalDateTime.now())
        this.kweetService.createKweet(kweet)

        return Response.ok(kweet).build()
    }

}