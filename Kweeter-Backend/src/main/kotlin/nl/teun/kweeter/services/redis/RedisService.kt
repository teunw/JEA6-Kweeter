package nl.teun.kweeter.services.redis

import redis.clients.jedis.Jedis

interface RedisService {
    fun getJedisInstance(): Jedis
}