package nl.teun.kweeter.services.redis

interface RedisSaveable<T> {
    fun saveString() : String

    fun parseFromString() : T
}