package nl.teun.kweeter.services

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.GsonBuilder
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.serializers.ProfileRestSerializer
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class ProfileServiceRestImpl : ProfileService() {

    @Inject
    private lateinit var kweetService: KweetService

    private val apiUrl = "http://localhost:8000/profiles/"

    private val applicationJson = MediaType.parse("application/json")

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val serializer by lazy {
        GsonBuilder().registerTypeAdapter(Profile::class.java, ProfileRestSerializer(this.kweetService)).create()
    }

    private fun findByParameter(parameters: Map<String, String> = mapOf()): List<Profile> {
        var getParameters = ""
        if (parameters.count() > 0) {
            parameters.map { "${it.key}=${it.value}&" }.forEach { getParameters += it }
            val indexAmpersand = getParameters.lastIndexOf('&')
            getParameters = getParameters.removeRange(indexAmpersand, indexAmpersand + 1)
        }

        val request = Request.Builder().url("${this.apiUrl}?$getParameters").get().build()
        val response = this.httpClient.newCall(request).execute()
        return this.serializer.fromJson(response.body()!!.string())
    }

    override fun findAll(maxResults: Int, offsetResults: Int): List<Profile> = findByParameter()

    override fun findById(id: Long): Profile {
        val params = mapOf(Pair("id", id.toString()))
        return findByParameter(params).first()
    }

    override fun findByEmail(email: String): Profile {
        val params = mapOf(Pair("email", email))
        return findByParameter(params).first()
    }

    override fun findByUsername(username: String): Profile {
        val params = mapOf(Pair("username", username))
        return findByParameter(params).first()
    }

    override fun updateProfile(profile: Profile) {
        val url = "${this.apiUrl}${profile.id}"
        val request = Request.Builder()
                .url(url)
                .put(RequestBody.create(applicationJson, this.serializer.toJson(profile)))
                .build()
        val response = this.httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("Response failed")
        }
    }

    override fun createProfile(profile: Profile) {
        val request = Request.Builder()
                .url(this.apiUrl)
                .put(RequestBody.create(applicationJson, this.serializer.toJson(profile)))
                .build()
        val response = this.httpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("Response failed")
        }
    }
}