package nl.teun.kweeter.controllers

import nl.teun.kweeter.controllers.types.response.KweetLikeResponse
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.KweetServiceImpl
import nl.teun.kweeter.services.ProfileService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@Path("/kweets/actions/{kweetId}")
class KweetActionController {

    @Inject
    private lateinit var kweetService : KweetService

    @Inject
    private lateinit var profileService : ProfileService

    @PUT
    @Path("/like/{profileId}")
    fun toggleLikeKweet(@PathParam("kweetId") kweetId: Long, @PathParam("profileId") profileId: Long): Response? {
        val kweet = this.kweetService.findById(kweetId)
        val profile = this.profileService.findById(profileId)
        val hasLiked = kweet.likedBy.contains(profile)

        if (hasLiked) {
            kweet.likedBy.remove(profile)
        } else {
            kweet.likedBy.add(profile)
        }

        this.kweetService.updateKweet(kweet)

        val res = KweetLikeResponse(!hasLiked, kweet)
        return Response.ok(res).build()
    }

}