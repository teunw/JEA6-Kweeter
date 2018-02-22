package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.mock.MockService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/mock")
class MockInitializerController {

    @Inject
    private lateinit var mockService : MockService

    @GET
    @Path("/")
    fun initMocks() {
        mockService.insertMockUsers()
        mockService.insertMockKweets()
    }
}