package nl.teun.kweeter.controllers

import nl.teun.kweeter.services.search.SearchService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("/search")
class SearchController {

    @Inject
    private lateinit var searchService: SearchService

    @GET
    @Path("/all/{query}")
    @Produces("application/json")
    fun doSearch(@PathParam("query") query: String) = Response.ok(this.searchService.search(query)).build()

    @GET
    @Path("/profiles/{query}")
    @Produces("application/json")
    fun doSearchProfiles(@PathParam("query") query: String) = Response.ok(this.searchService.searchProfiles(query)).build()

    @GET
    @Path("/kweets/{query}")
    @Produces("application/json")
    fun doSearchKweets(@PathParam("query") query: String) = Response.ok(this.searchService.searchKweets(query)).build()

}