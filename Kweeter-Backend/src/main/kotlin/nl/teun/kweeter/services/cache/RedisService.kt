package nl.teun.kweeter.services.cache

import com.google.gson.Gson
import redis.clients.jedis.Jedis
import java.util.*
import javax.ejb.Stateless

@Stateless
class RedisService {

    private val jedis = Jedis("localhost", 6379)
    private val uuid = UUID.randomUUID()
    private val gson = Gson()

    fun getJedis(): Jedis {
        return this.jedis
    }

    fun cacheObject(obj: Any, cacheName: String) {
        jedis.rpush(this.uuid.toString(), gson.toJson(obj))
    }

    fun getCachedObjects(cacheName: String): List<String> {
        return jedis.lrange(this.uuid.toString(), 0, -1)
    }

    fun hasCachedObjects(): Boolean {
        return jedis.llen(this.uuid.toString()) > 0
    }

    fun invalidate() {
        jedis.del(this.uuid.toString())
    }
}