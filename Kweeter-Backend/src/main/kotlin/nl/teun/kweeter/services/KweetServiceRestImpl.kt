package nl.teun.kweeter.services

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import nl.teun.kweeter.deserializers.KweetRestDeserializer
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.toJson
import okhttp3.*
import java.io.Serializable
import java.time.LocalDateTime
import javax.ejb.Stateless
import javax.enterprise.inject.Default
import javax.inject.Inject

@Default
@Stateless
class KweetServiceRestImpl : KweetService() {

    @Inject
    private lateinit var profileService: ProfileService

    private val apiUrl: HttpUrl.Builder by lazy {
        HttpUrl.Builder()
                .host("localhost")
                .port(8000)
                .scheme("http")
                .addPathSegment("kweets")
    }
    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private fun findWithParameter(parameter: String, value: String): List<Kweet> {
        val url = this.apiUrl.addQueryParameter(parameter, value).build()
        val request = Request.Builder().url(url).get().build()
        val response = this.httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("Could not retrieve kweet by publicId")
        }
        val body = response.body()!!.string()
        return this.gsonDeserializer.fromJson(body)
    }

    private val gsonDeserializer by lazy {
        GsonBuilder().registerTypeAdapter(Kweet::class.java, KweetRestDeserializer(this.profileService)).create()
    }

    override fun findAll(maxResults: Int, offsetResults: Int): List<Kweet> {
        val allRequest = Request.Builder().url(apiUrl.build()).get().build()
        val response = this.httpClient.newCall(allRequest).execute()
        if (!response.isSuccessful) {
            throw Exception("Could not retrieve kweets")
        }
        return this.gsonDeserializer.fromJson(response.body()!!.string())
    }

    override fun findById(id: Long): Kweet {
        val allRequest = Request.Builder().url("$apiUrl/$id").get().build()
        val response = this.httpClient.newCall(allRequest).execute()
        if (!response.isSuccessful) {
            throw Exception("Could not retrieve kweets")
        }
        val body = response.body()!!.string()
        return this.gsonDeserializer.fromJson(body)
    }

    override fun findByPublicId(id: String): Kweet {
        val kweets = this.findWithParameter("publicId", id)
        if (kweets.size != 1) {
            throw Exception("Did not find right amount of kweets")
        }
        return kweets.first()
    }

    override fun findByProfile(profile: Profile): List<Kweet> = this.findWithParameter("author", profile.id.toString())

    override fun updateKweet(kweet: Kweet) {
        val request = Request.Builder().url(this.apiUrl.build()).put(createJsonRequestBody(kweet)).build()
        val response = this.httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("Response failed")
        }
    }

    override fun createKweet(kweet: Kweet) {
        val request = Request.Builder().url(this.apiUrl.build()).post(createJsonRequestBody(kweet)).build()
        val response = this.httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("Response failed")
        }
    }

    override fun deleteKweet(kweet: Kweet) {
        val request = Request.Builder().url(this.apiUrl.addPathSegment("${kweet.internalId}").build()).delete().build()
        val response = this.httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("Response failed")
        }
    }

    override fun getKweetsAfter(dateTime: LocalDateTime): List<Kweet> {
        throw NotImplementedError("")
    }

    companion object {
        fun createJsonRequestBody(obj: Serializable) = RequestBody.create(MediaType.parse("application/json"), obj.toJson())
    }
}