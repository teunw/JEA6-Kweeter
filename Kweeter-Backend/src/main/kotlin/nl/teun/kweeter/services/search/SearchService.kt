package nl.teun.kweeter.services.search

import nl.teun.kweeter.facades.KweetFacade
import nl.teun.kweeter.facades.ProfileFacade

abstract class SearchService {

    open fun search(query: String): List<Any> = this.searchProfiles(query).plus(this.searchKweets(query))

    abstract fun searchProfiles(query: String): List<ProfileFacade>

    abstract fun searchKweets(query: String): List<KweetFacade>
}