package nl.teun.kweeter.services.search

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile

abstract class SearchService {

    fun search(query: String): List<Any> = this.searchProfiles(query).plus(this.searchKweets(query))

    abstract fun searchProfiles(query: String): List<Profile>

    abstract fun searchKweets(query: String): List<Kweet>
}