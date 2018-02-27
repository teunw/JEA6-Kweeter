package nl.teun.kweeter.services

import com.google.gson.Gson
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.cache.RedisService
import java.util.*
import javax.ejb.Stateless
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class KweetServiceImpl : KweetService {

    @PersistenceContext
    private lateinit var entityManager : EntityManager

    @Inject
    private lateinit var redisCache: RedisService

    private val gson = Gson()
    private val CacheName = "Cache_Kweets"

    override fun findAll(maxResults: Int, offsetResults: Int): List<Kweet> {
        if (redisCache.hasCachedObjects()) {
            System.out.println("Getting cached objects")
            return redisCache
                    .getCachedObjects(CacheName)
                    .map { it -> gson.fromJson(it, Kweet::class.java) }
                    .sortedByDescending { it.date.time }
        }
        val kweets = this.entityManager
                .createNamedQuery("Kweet.all")
                .setMaxResults(maxResults)
                .setFirstResult(offsetResults)
                .resultList
                .filterIsInstance<Kweet>()
                .toList()
                .sortedByDescending { it.date.time }
        kweets.forEach { it -> redisCache.cacheObject(it, CacheName) }
        return kweets
    }

    override fun findById(id : Long): Kweet {
        val returnList = this.entityManager
                .createNamedQuery("Kweet.findbyid")
                .setParameter("k_id", id)
                .resultList
                .filterIsInstance<Kweet>()
        return returnList.first()
    }

    override fun findByProfile(profile : Profile) : List<Kweet> {
        return this.entityManager
                .createNamedQuery("Kweet.findbyprofile")
                .setParameter("p_id", profile.id)
                .resultList
                .filterIsInstance<Kweet>()
    }

    override fun updateKweet(kweet: Kweet) {
        this.entityManager.merge(kweet)
        this.redisCache.invalidate()
    }

    override fun createKweet(kweet: Kweet) {
        if (kweet.publicId == null) {
            kweet.setPublicId(UUID.randomUUID())
        }
        this.entityManager.persist(kweet)
        this.redisCache.invalidate()
    }
}