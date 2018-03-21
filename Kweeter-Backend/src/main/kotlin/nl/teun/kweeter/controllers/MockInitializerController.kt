package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.mock.MockService
import nl.teun.kweeter.services.search.KweeterSearchService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/mock")
class MockInitializerController {

    @Inject
    private lateinit var elasticSearchService: KweeterSearchService

    @GET
    @Path("/")
    fun initMocks() {
        elasticSearchService.updateSearchIndex()
    }
}