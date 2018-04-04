package nl.teun.kweeter.controllers

import nl.teun.kweeter.authentication.annotations.KweeterAuthRequired
import nl.teun.kweeter.controllers.types.request.KweetPost
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.facades.KweetFacade
import nl.teun.kweeter.httpResponseBadRequest
import nl.teun.kweeter.httpResponseNotFound
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.toKweetFacade
import java.time.LocalDateTime
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

    @GET
    @Path("/")
    @Produces
    fun getKweets(@QueryParam("results") results: Int, @QueryParam("page") page: Int): Response? {
        if (results > 100) {
            return httpResponseNotFound().entity("For performance reasons > 100 results is not allowed").build()
        }
        val amountOfResults = if (results != 0) results else 50
        val kweets = kweetService.findAll(amountOfResults, page * results).map { it.toKweetFacade() }
        return Response.ok(kweets).build()
    }

    @GET
    @Path("/{kweetId}")
    @Produces
    fun getKweet(@PathParam("kweetId") kweetId: String): Response {
        if (kweetId.isEmpty()) {
            return httpResponseBadRequest().entity("kweetId is empty").build()
        }
        val kweetIdAsLong = kweetId.toLongOrNull() ?: return Response.ok(kweetService.findByPublicId(kweetId).toKweetFacade()).build()
        return Response.ok(kweetService.findById(kweetIdAsLong).toKweetFacade()).build()
    }

    @KweeterAuthRequired
    @PUT
    @Path("/{kweetId}")
    fun updateKweet(
            @PathParam("kweetId") kweetId: String,
            parameters: KweetFacade,
            @Context securityContext: SecurityContext
    ): Response {
        if (parameters.author != null) {
            return httpResponseBadRequest().entity("profileId is empty").build()
        }
        if (parameters.textContent!!.isEmpty()) {
            return httpResponseBadRequest().entity("textContent is empty").build()
        }

        val kweet = this.kweetService.findByPublicId(kweetId)
        kweet.author = this.profileService.findByPrincipal(securityContext.userPrincipal)
        kweet.textContent = parameters.textContent
        return Response.ok(kweet.toKweetFacade()).build()
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
        val kweets = this.kweetService.findByProfile(profile).map { it.toKweetFacade() }
        return Response.ok(kweets).build()
    }

    @KweeterAuthRequired
    @POST
    @Path("/")
    fun createKweet(
            requestPost: KweetPost,
            @Context securityContext: SecurityContext
    ): Response {
        if (requestPost.textContent!!.isEmpty()) {
            return httpResponseBadRequest().entity("textContent is empty (\"${requestPost.textContent}\"").build()
        }
        val kweet = Kweet()
                .setAuthor(this.profileService.findByPrincipal(securityContext.userPrincipal))
                .setTextContent(requestPost.textContent)
                .setDate(LocalDateTime.now())
                .setLikedBy(emptyList())
        this.kweetService.createKweet(kweet)
        return Response.ok(kweet.toKweetFacade()).build()
    }

}