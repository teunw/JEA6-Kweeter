package nl.teun.kweeter.services.mock

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.services.ProfileService
import org.apache.commons.lang3.RandomStringUtils
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

    fun insertMockUsers() {
        for (i in 0..profilesAndKweets) {
            val profile = Profile()
                    .setEmail(RandomStringUtils.randomAlphanumeric(17) + "@email.com")
                    .setUsername(RandomStringUtils.randomAlphanumeric(64))
                    .setDisplayName(RandomStringUtils.randomAlphanumeric(32))
            profileService.createProfile(profile)
        }
    }

    fun insertMockKweets() {
        for (i in 0..profilesAndKweets) {
            val kweet = Kweet()
                    .setTextContent(RandomStringUtils.randomAlphanumeric(17) + "@email.com")
                    .setAuthor(profileService.findById((random.nextInt(profilesAndKweets) + 1).toLong()))
            kweetService.createKweet(kweet)
        }
    }
}
