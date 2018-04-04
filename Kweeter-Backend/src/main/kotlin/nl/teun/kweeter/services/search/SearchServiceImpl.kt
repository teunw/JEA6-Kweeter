package nl.teun.kweeter.services.search

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.KweetFacade
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.toKweetFacade
import nl.teun.kweeter.toProfileFacade
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

    private fun getFullTextEntityManager(): FullTextEntityManager {
        if (this.fullTextEntityManager != null) {
            return this.fullTextEntityManager!!
        }
        val entityManager = this.entityManagerFactory.createEntityManager()
        this.fullTextEntityManager = Search.getFullTextEntityManager(entityManager)
        this.fullTextEntityManager?.createIndexer()?.startAndWait()
        return this.fullTextEntityManager!!
    }

    override fun searchProfiles(query: String): List<ProfileFacade> {
        val profileQueryBuilder = this.getFullTextEntityManager().searchFactory.buildQueryBuilder().forEntity(Profile::class.java).get()
        val profileQuery = profileQueryBuilder.keyword().onFields("username", "email", "location", "bio", "contactLink").matching(query).createQuery()
        return this.getFullTextEntityManager().createFullTextQuery(profileQuery).resultList.filterIsInstance<Profile>().map { it.toProfileFacade() }
    }

    override fun searchKweets(query: String): List<KweetFacade> {
        val tweetQb = this.getFullTextEntityManager().searchFactory.buildQueryBuilder().forEntity(Kweet::class.java).get()
        val fullTextQuery = tweetQb.keyword().onFields("textContent").matching(query).createQuery()
        return this.getFullTextEntityManager().createFullTextQuery(fullTextQuery).resultList.filterIsInstance<Kweet>().map { it.toKweetFacade() }
    }
}