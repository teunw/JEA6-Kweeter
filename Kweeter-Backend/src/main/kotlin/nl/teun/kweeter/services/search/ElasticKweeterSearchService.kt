package nl.teun.kweeter.services.search

import nl.teun.kweeter.facades.KweetFacade
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import nl.teun.kweeter.toJson
import nl.teun.kweeter.toKweetFacade
import nl.teun.kweeter.toProfileFacade
import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.action.update.UpdateResponse
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentType
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class ElasticKweeterSearchService {

    private val ElasticServer = "elasticsearch.teunwillems.nl"
    private val elasticClient = RestHighLevelClient(RestClient.builder(HttpHost(this.ElasticServer, 443, "https")))

    @Inject
    private lateinit var kweetService: KweetService

    @Inject
    private lateinit var profileService: ProfileService

    fun updateSearchIndex() {
        val kweets = this.kweetService.findAll(Int.MAX_VALUE, 0).map { it.toKweetFacade() }
        val profiles = this.profileService.findAll(Int.MAX_VALUE, 0).map { it.toProfileFacade() }

        val indexRequests = ArrayList<IndexRequest>(kweets.size + profiles.size)
        indexRequests.addAll(kweets.map { IndexRequest("profiles", "kweet", it.publicId.toString()).source(it.toJson(), XContentType.JSON) })
        indexRequests.addAll(profiles.map { IndexRequest("profiles", "profile", it.id.toString()).source(it.toJson(), XContentType.JSON) })
        indexRequests.forEach { this.elasticClient.index(it) }
        println("Done")
    }

    fun addProfileToIndex(profileFacade: ProfileFacade): IndexResponse {
        val insertRequest = IndexRequest("profile", "profile", profileFacade.id.toString()).source(profileFacade.toJson(), XContentType.JSON)
        return this.elasticClient.index(insertRequest)
    }

    fun updateProfileIndex(profileFacade: ProfileFacade): UpdateResponse {
        val updateRequest = UpdateRequest("profiles", "profile", profileFacade.id.toString()).doc(profileFacade.toJson(), XContentType.JSON)
        return this.elasticClient.update(updateRequest)
    }

    fun addKweetToIndex(kweetFacade: KweetFacade): IndexResponse {
        val insertRequest = IndexRequest("profiles", "kweet", kweetFacade.publicId.toString()).source(kweetFacade.toJson(), XContentType.JSON)
        return this.elasticClient.index(insertRequest)
    }

    fun updateKweetIndex(kweetFacade: KweetFacade): UpdateResponse {
        val updateRequest = UpdateRequest("profiles", "kweet", kweetFacade.publicId.toString()).doc(kweetFacade.toJson(), XContentType.JSON)
        return this.elasticClient.update(updateRequest)
    }
}