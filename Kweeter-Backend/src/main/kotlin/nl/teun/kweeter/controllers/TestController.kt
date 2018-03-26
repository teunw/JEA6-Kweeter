package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.search.SearchService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/test")
class TestController {

    @Inject
    private lateinit var searchService: SearchService

    @GET
    @Path("/")
    fun getTest() {
        this.searchService.search("car")
    }
}