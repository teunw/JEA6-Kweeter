package nl.teun.kweeter.controllers

import nl.teun.kweeter.authentication.annotations.KweeterAuthRequired
import nl.teun.kweeter.controllers.types.response.KweetLikeResponse
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.toKweetFacade
import javax.inject.Inject
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/kweets/actions/{kweetId}")
class KweetActionController {

    @Inject
    private lateinit var kweetService: KweetService

    @Inject
    private lateinit var profileService: ProfileService

    @KweeterAuthRequired
    @PUT
    @Path("/like")
    fun toggleLikeKweet(@PathParam("kweetId") kweetId: String,
                        @Context securityContext: SecurityContext): Response? {
        val kweet = this.kweetService.findByPublicId(kweetId)
        val profile = this.profileService.findByPrincipal(securityContext.userPrincipal)
        val hasLiked = kweet.likedBy.contains(profile)

        if (hasLiked) {
            kweet.likedBy.remove(profile)
        } else {
            kweet.likedBy.add(profile)
        }

        this.kweetService.updateKweet(kweet)

        val res = KweetLikeResponse(!hasLiked, kweet.toKweetFacade())
        return Response.ok(res).build()
    }
}