package nl.teun.kweeter.services.mock

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.lang3.RandomStringUtils
import java.time.LocalDateTime
import java.util.*
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class MockService {

    @Inject
    private lateinit var profileService: ProfileService

    @Inject
    private lateinit var kweetService: KweetService

    private val random = Random()
    private val profilesAndKweets = 999

    fun insertFollowers() {
        val profiles = this.profileService.findAll(Int.MAX_VALUE, 0)
        for (i in 0..25) {
            val profilesToRetrieveFollowers = getRandomElement(profiles)
            for (x in 0..25) {
                val profileToFollow = getRandomElement(profiles)
            }
        }
    }

    fun <T> getRandomElement(elements: List<T>): T {
        return elements[random.nextInt(elements.size - 1)]
    }

    fun insertMockUsers() {
        val sentences = getSentences()
        for (i in 0..profilesAndKweets) {
            val profile = Profile()
                    .setEmail(RandomStringUtils.randomAlphanumeric(17) + "@email.com")
                    .setUsername(RandomStringUtils.randomAlphanumeric(8))
                    .setDisplayName(RandomStringUtils.randomAlphanumeric(7))
                    .setPassword("password")
                    .setBio(sentences[random.nextInt(sentences.size - 1)])
            profileService.createProfile(profile)
        }
    }

    fun insertMockKweets() {
        val sentences = getSentences()
        val profiles = this.profileService.findAll(Int.MAX_VALUE, 0)
        val date = LocalDateTime.now()
        for (i in 0..profilesAndKweets) {
            val newDate = date.minusMinutes(random.nextInt(90000).toLong())
            val kweet = Kweet()
                    .setTextContent(getRandomElement(sentences))
                    .setAuthor(getRandomElement(profiles))
                    .setDateWithLocalDateTime(newDate)
            kweetService.createKweet(kweet)
        }
    }

    private inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    fun getSentences(): List<String> {
        val url = "https://randomwordgenerator.com/json/sentences.json"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute().body()!!.string()
        val obj = Gson().fromJson<WordGeneratorResult>(response)
        return obj.data.map { it -> it.sentence }
    }
}
