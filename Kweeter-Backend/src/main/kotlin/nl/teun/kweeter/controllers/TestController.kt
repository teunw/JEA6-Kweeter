package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.mock.MockService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/test")
class TestController {

    @Inject
    private lateinit var mockService: MockService

    @GET
    @Path("/")
    fun getTest() {
        this.mockService.insertMockUsers()
        this.mockService.insertMockKweets()
        this.mockService.insertFollowers()
    }
}