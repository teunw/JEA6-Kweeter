package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.search.KweeterSearchService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/test")
class TestController {

    @Inject
    private lateinit var searchService: KweeterSearchService

    @GET
    @Path("/")
    fun getTest() {
        this.searchService.updateSearchIndex()
    }
}