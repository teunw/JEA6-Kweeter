package nl.teun.kweeter.services.search

import com.google.gson.Gson
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import org.apache.http.HttpHost
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
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
        val kweets = this.kweetService.findAll(Int.MAX_VALUE, 0)
        val profiles = this.profileService.findAll(Int.MAX_VALUE, 0)

        val bulkRequest = BulkRequest()
        kweets.forEach { it -> bulkRequest.add(IndexRequest("kweets", "kweet", it.internalId.toString()).source(gson.toJson(it), XContentType.JSON)) }
        profiles.forEach { it -> bulkRequest.add(IndexRequest("profiles", "profile", it.id.toString()).source(gson.toJson(it), XContentType.JSON)) }
        return this.elasticClient.bulk(bulkRequest).hasFailures()
    }
}