package nl.teun.kweeter.test.domain

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.KweetResponse
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.domain.Rekweet
import nl.teun.kweeter.toJavaUtilDate
import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.util.*

class KweetDomainTest {

    private var kweet1 = Kweet()
    private var kweet2 = Kweet()

    private var profile1 = Profile()
    private var profile2 = Profile()

    @Before
    fun setUp() {
        this.kweet1 = Kweet()
                .setTextContent("This is kweet1")
                .setAuthor(profile1)
                .setDateWithLocalDateTime(LocalDateTime.now())
                .setPublicId(UUID.randomUUID())
        this.kweet2 = Kweet()
                .setTextContent("This is kweet2")
                .setAuthor(profile2)
                .setDateWithLocalDateTime(LocalDateTime.now())
                .setPublicId(UUID.randomUUID())
        this.kweet1.internalId = 1
        this.kweet2.internalId = 2
    }

    @Test
    fun getPublicId() {
        Assert.assertTrue(this.kweet1.publicId != null)
        Assert.assertTrue(this.kweet2.publicId != null)
    }

    @Test
    fun getInternalId() {
        Assert.assertEquals(this.kweet1.internalId, 1)
        Assert.assertEquals(this.kweet2.internalId, 2)
    }

    @Test
    fun getTextContent() {
        Assert.assertEquals(this.kweet1.textContent, "This is kweet1")
        Assert.assertEquals(this.kweet2.textContent, "This is kweet2")
    }

    @Test
    fun setTextContent() {
        val kweetText = "This is kweet1 set"
        this.kweet1.textContent = kweetText
        Assert.assertEquals(this.kweet1.textContent, kweetText)
    }

    @Test
    fun getAuthor() {
        Assert.assertEquals(this.kweet1.author, profile1)
        Assert.assertEquals(this.kweet2.author, profile2)
    }

    @Test
    fun setAuthor() {
        this.kweet1.author = profile2
        Assert.assertEquals(this.kweet1.author, profile2)
    }

    @Test
    fun getDate() {
        Assert.assertEquals(this.kweet1.date.toString(), java.util.Date().toString())
    }

    @Test
    fun setDate() {
        val time = java.util.Date(java.util.Date().time + 2000L)
        this.kweet1.date = time
        Assert.assertEquals(this.kweet1.date, time)
    }

    @Test
    fun setDateWithLocalDateTime() {
        val localDateLater = LocalDateTime.now().plusDays(1)
        this.kweet1.setDateWithLocalDateTime(localDateLater)
        Assert.assertEquals(this.kweet1.date, localDateLater.toJavaUtilDate())
    }

    @Test
    fun getResponses() {
        Assert.assertEquals(this.kweet1.responses.size, 0)
    }

    @Test
    fun setResponses() {
        val kweetRes = KweetResponse()
        kweetRes.ParentKweet = kweet1
        this.kweet1.responses.add(kweetRes)
        Assert.assertEquals(this.kweet1.responses.size, 1)

        this.kweet1.setResponses(mutableListOf(kweetRes))
        Assert.assertEquals(this.kweet1.responses.size, 1)
    }

    @Test
    fun getLikedBy() {
        Assert.assertEquals(this.kweet1.likedBy.size, 0)
    }

    @Test
    fun setLikedBy() {
        this.kweet1.likedBy.add(profile2)
        Assert.assertEquals(this.kweet1.likedBy.size, 1)

        this.kweet1.setLikedBy(mutableListOf(profile2))
        Assert.assertEquals(this.kweet1.likedBy.size, 1)
    }

    @Test
    fun getRekweets() {
        Assert.assertEquals(this.kweet1.rekweets.size, 0)
    }

    @Test
    fun setRekweets() {
        val rekweet = Rekweet()
        rekweet.parentKweet = kweet1
        this.kweet1.rekweets.add(rekweet)
        Assert.assertEquals(this.kweet1.rekweets.size, 1)

        this.kweet1.setRekweets(mutableListOf(rekweet))
        Assert.assertEquals(this.kweet1.rekweets.size, 1)

        Assert.assertEquals(this.kweet1.rekweets.toMutableList()[0], rekweet)
        Assert.assertEquals(this.kweet1.rekweets.toMutableList()[0].parentKweet, kweet1)
    }

    @Test
    fun testEquals() {
        Assert.assertNotEquals(kweet1, kweet2)
        Assert.assertNotEquals(kweet2, kweet1)
        Assert.assertEquals(kweet1, kweet1)
        Assert.assertEquals(kweet2, kweet2)
        Assert.assertTrue(kweet1.equals(kweet1))
    }

    @Test
    fun testHashCode() {
        Assert.assertEquals(kweet1.hashCode(), kweet1.hashCode())
        Assert.assertEquals(kweet2.hashCode(), kweet2.hashCode())
        Assert.assertNotEquals(kweet1.hashCode(), kweet2.hashCode())
    }

    companion object {
        @Deployment
        fun createDeployment(): JavaArchive {
            return ShrinkWrap.create(JavaArchive::class.java)
                    .addClass(Kweet::class.java)
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        }
    }
}
