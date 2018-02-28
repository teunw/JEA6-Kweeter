package nl.teun.kweeter.services.cache

import com.google.gson.Gson
import redis.clients.jedis.Jedis
import java.util.*
import javax.ejb.Stateless

@Stateless
class RedisService {

    private var jedis = Jedis("localhost", 6379)
    private val gson = Gson()

    fun getJedis(): Jedis {
        return this.jedis
    }

    fun cacheObject(obj: Any, cacheName:String) {
        jedis.rpush(cacheName, gson.toJson(obj))
    }

    fun getCachedObjects(cacheName:String): List<String> {
        return jedis.lrange(cacheName, 0, -1)
    }

    fun hasCachedObjects(cacheName:String): Boolean {
        return jedis.llen(cacheName) > 0
    }

    fun invalidate(cacheName:String) {
        jedis.del(cacheName)
    }
}