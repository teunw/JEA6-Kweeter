package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.search.ElasticKweeterSearchService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/mock")
class MockInitializerController {

    @Inject
    private lateinit var elasticSearchService: ElasticKweeterSearchService

    @GET
    @Path("/")
    fun initMocks() {
        elasticSearchService.updateSearchIndex()
    }
}