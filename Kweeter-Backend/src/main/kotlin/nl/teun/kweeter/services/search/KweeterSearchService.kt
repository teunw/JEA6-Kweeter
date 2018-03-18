package nl.teun.kweeter.services.search

import com.google.gson.Gson
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.toKweetFacade
import nl.teun.kweeter.toProfileFacade
import org.apache.http.HttpHost
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class KweeterSearchService {

    private val xContentType = XContentType.fromMediaType("application/json")
    private val ElasticServer = "elasticsearch.teunwillems.nl"
    private val elasticClient = RestHighLevelClient(RestClient.builder(HttpHost(this.ElasticServer, 443, "https")))
    private val gson = Gson()

    @Inject
    private lateinit var kweetService: KweetService

    @Inject
    private lateinit var profileService: ProfileService

    fun updateSearchIndex(): Boolean {
        val deleteReq = DeleteRequest("/")
        this.elasticClient.delete(deleteReq)

        val kweets = this.kweetService.findAll(Int.MAX_VALUE, 0).map { it.toKweetFacade() }
        val profiles = this.profileService.findAll(Int.MAX_VALUE, 0).map { it.toProfileFacade() }

        val bulkRequest = BulkRequest()
        kweets.forEach { bulkRequest.add(IndexRequest("kweets", "kweet", it.publicId.toString()).source(gson.toJson(it), XContentType.JSON)) }
        profiles.forEach { bulkRequest.add(IndexRequest("profiles", "profile", it.id.toString()).source(gson.toJson(it), XContentType.JSON)) }
        return this.elasticClient.bulk(bulkRequest).hasFailures()
    }

    fun addProfileToIndex(profileFacade: ProfileFacade): IndexResponse {
        val insertRequest = IndexRequest("profile", "profile", profileFacade.id.toString()).source(gson.toJson(profileFacade), XContentType.JSON)
        return this.elasticClient.index(insertRequest)
    }

}