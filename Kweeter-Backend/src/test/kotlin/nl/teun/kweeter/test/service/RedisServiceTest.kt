package nl.teun.kweeter.test.service

import com.google.gson.Gson
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.services.cache.RedisService
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import redis.embedded.RedisServer

class RedisServiceTest {

    private var redisServer = RedisServer(9999)
    private var redisService = RedisService(9999)
    private var gson = Gson()

    @Before
    fun beforeClass() {
        redisServer.start()
    }

    @After
    fun teardown() {
        redisServer.stop()
    }

    @Test
    fun test() {
        val cacheName = "REDIS_SERVICE_TEST_CACHE"
        val cacheObject = Kweet().setTextContent("cached")
        val notCachedObject = Kweet().setTextContent("not cached")

        Assert.assertFalse(redisService.hasCachedObjects(cacheName))
        redisService.cacheObject(cacheObject, cacheName)
        Assert.assertTrue(redisService.hasCachedObjects(cacheName))

        val cachedObjects = redisService.getCachedObjects(cacheName)
                .map { it -> gson.fromJson(it, Kweet::class.java) }
        Assert.assertTrue(cachedObjects.filter { it -> it.textContent == cacheObject.textContent }.size == 1)
        Assert.assertFalse(cachedObjects.contains(notCachedObject))

        redisService.invalidate(cacheName)
        Assert.assertFalse(redisService.hasCachedObjects(cacheName))

        Assert.assertNotEquals(redisService.getJedis(), null)
    }
}