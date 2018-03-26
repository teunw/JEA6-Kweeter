package nl.teun.kweeter.services.search

import com.google.gson.Gson
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import org.hibernate.search.jpa.FullTextEntityManager
import org.hibernate.search.jpa.Search
import javax.ejb.Stateless
import javax.persistence.EntityManagerFactory
import javax.persistence.PersistenceUnit


@Stateless
class SearchServiceImpl : SearchService() {

    @PersistenceUnit
    private lateinit var entityManagerFactory: EntityManagerFactory

    private var fullTextEntityManager: FullTextEntityManager? = null

    fun getFullTextEntityManager(): FullTextEntityManager {
        if (this.fullTextEntityManager != null) {
            return this.fullTextEntityManager!!
        }
        val entityManager = this.entityManagerFactory.createEntityManager()
        this.fullTextEntityManager = Search.getFullTextEntityManager(entityManager)
        this.fullTextEntityManager?.createIndexer()?.startAndWait()
        return this.fullTextEntityManager!!
    }

    override fun searchProfiles(query: String): List<Profile> {
        getFullTextEntityManager()

        val profiles = emptyList<Profile>()
        println(Gson().toJson(profiles))
        return emptyList()
    }

    override fun searchKweets(query: String): List<Kweet> {
        val tweetQb = this.getFullTextEntityManager().searchFactory.buildQueryBuilder().forEntity(Kweet::class.java).get()
        val fullTextQuery = tweetQb.keyword().onFields().matching(query).createQuery()
        val results = this.getFullTextEntityManager().createFullTextQuery(fullTextQuery).resultList.filterIsInstance<Kweet>()
        return results
    }
}