package nl.teun.kweeter.services.cache

import com.google.gson.Gson
import redis.clients.jedis.Jedis
import javax.ejb.Stateless

@Stateless
@Deprecated("Redis is not used anymore")
class RedisService(port : Int = 6379) {

    private var jedis = Jedis("localhost", port)
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