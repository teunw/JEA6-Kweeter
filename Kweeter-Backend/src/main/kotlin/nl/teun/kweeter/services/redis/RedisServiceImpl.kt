package nl.teun.kweeter.services.redis

import redis.clients.jedis.Jedis
import javax.ejb.Stateless

@Stateless
class RedisServiceImpl : RedisService {

    private var unsafeJedis: Jedis? = null

    override fun getJedisInstance(): Jedis {
        if (unsafeJedis == null)
            this.unsafeJedis = Jedis("localhost")
        return unsafeJedis as Jedis
    }
}