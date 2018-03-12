package nl.teun.kweeter.controllers

import nl.teun.kweeter.authentication.annotations.AuthenticatedUser
import nl.teun.kweeter.authentication.annotations.KweeterAuthRequired
import nl.teun.kweeter.controllers.types.request.KweetPost
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.KweetResponse
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.httpResponseBadRequest
import nl.teun.kweeter.httpResponseNotFound
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/kweets")
class KweetController {

    @Inject
    private lateinit var kweetService: KweetService

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    @AuthenticatedUser
    private lateinit var authenticatedUser: Profile

    @GET
    @Path("/")
    @Produces
    fun getKweets(@QueryParam("results") results: Int, @QueryParam("page") page: Int): Response? {
        if (results > 100) {
            return httpResponseNotFound().entity("For performance reasons > 100 results is not allowed").build()
        }
        val amountOfResults = if (results != 0) results else 50
        return Response.ok(kweetService.findAll(amountOfResults, page * results)).build()
    }

    @GET
    @Path("/{kweetId}")
    @Produces
    fun getKweet(@PathParam("kweetId") kweetId: String): Response {
        if (kweetId.isEmpty()) {
            return httpResponseBadRequest().entity("kweetId is empty").build()
        }
        val kweetIdAsLong = kweetId.toLongOrNull()
                ?: return Response.serverError().entity("profileId not a long").build()

        return Response.ok(kweetService.findById(kweetIdAsLong)).build()
    }

    @KweeterAuthRequired
    @PUT
    @Path("/{kweetId}")
    fun updateKweet(
            @PathParam("kweetId") kweetId: Long,
            @QueryParam("profileId") profileId: String,
            @QueryParam("textContent") textContent: String,
            @Context securityContext: SecurityContext
    ): Response {
        if (profileId.isEmpty()) {
            return httpResponseBadRequest().entity("profileId is empty").build()
        }
        if (textContent.isEmpty()) {
            return httpResponseBadRequest().entity("textContent is empty").build()
        }

        val kweet = this.kweetService.findById(kweetId)
        kweet.author = this.profileService.findByPrincipal(securityContext.userPrincipal)
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
                ?: return httpResponseBadRequest().entity("Profile ID not a long").build()
        val profile = this.profileService.findById(longProfileId)
        val kweets = this.kweetService.findByProfile(profile)
        return Response.ok(kweets).build()
    }

    @KweeterAuthRequired
    @POST
    @Path("/")
    fun createKweet(
            requestPost: KweetPost,
            @Context securityContext: SecurityContext
    ): Response {
        if (requestPost.textContent.isEmpty()) {
            return httpResponseBadRequest().entity("textContent is empty (\"${requestPost.textContent}\"").build()
        }
        var kweet = Kweet()
        if (requestPost.responseToKweetId > 0) {
            kweet = KweetResponse()
            val responseKweet = kweet
            responseKweet.ParentKweet = responseKweet
        }

        kweet.textContent = requestPost.textContent
        kweet.author = this.profileService.findByPrincipal(securityContext.userPrincipal)
        kweet.setPublicId(UUID.randomUUID())
        kweet.setDateWithLocalDateTime(LocalDateTime.now())

        kweet.setLikedBy(mutableListOf())
        kweet.setRekweets(mutableListOf())
        kweet.setResponses(mutableListOf())

        this.kweetService.createKweet(kweet)
        return Response.ok(kweet).build()
    }

}