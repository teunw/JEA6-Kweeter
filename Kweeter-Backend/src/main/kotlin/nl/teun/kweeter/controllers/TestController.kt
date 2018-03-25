package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.search.ElasticKweeterSearchService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/test")
class TestController {

    @Inject
    private lateinit var searchService: ElasticKweeterSearchService

    @GET
    @Path("/")
    fun getTest() {
        this.searchService.updateSearchIndex()
    }
}