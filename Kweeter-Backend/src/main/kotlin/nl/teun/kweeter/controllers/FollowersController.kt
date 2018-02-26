package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.FollowerService
import nl.teun.kweeter.services.ProfileService
import javax.inject.Inject
import javax.ws.rs.Path


@Path("/profiles")
class FollowersController {

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var followService: FollowerService
}