package nl.teun.kweeter.controllers

import nl.teun.kweeter.authentication.annotations.KweeterAuthRequired
import nl.teun.kweeter.services.FollowerService
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.toFollowerFacade
import nl.teun.kweeter.toProfileFacade
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext


@Path("/followers")
class FollowersController {

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var followersService: FollowerService

    @GET
    @Path("/{profileId}")
    fun getFollowersForProfile(@PathParam("profileId") profileId: Long): Response {
        val profile = this.profileService.findById(profileId)
        val follower = this.followersService.getFollowers(profile)
        return Response.ok(follower.toFollowerFacade()).build()
    }

    @KweeterAuthRequired
    @POST
    @Path("/follow/{profileToFollow}")
    fun addFollower(@PathParam("profileToFollow") profileToFollowId: Long,
                    @Context securityContext: SecurityContext): Response {
        val authenticatedProfile = this.profileService.findByPrincipal(securityContext.userPrincipal)
        val profileToFollow = this.profileService.findById(profileToFollowId)
        this.followersService.addFollower(authenticatedProfile, profileToFollow)
        return Response.ok(authenticatedProfile.toProfileFacade()).build()
    }

    @KweeterAuthRequired
    @DELETE
    @Path("/follow/{profileToFollow}")
    fun removeFollower(@PathParam("profileToUnfollow") profileToUnfollowId: Long,
                       @Context securityContext: SecurityContext): Response {
        val authenticatedProfile = this.profileService.findByPrincipal(securityContext.userPrincipal)
        val profileToFollow = this.profileService.findById(profileToUnfollowId)
        this.followersService.removeFollower(authenticatedProfile, profileToFollow)
        return Response.ok(authenticatedProfile.toProfileFacade()).build()
    }

}