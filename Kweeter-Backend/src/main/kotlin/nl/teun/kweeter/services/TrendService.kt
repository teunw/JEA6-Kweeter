package nl.teun.kweeter.services

import nl.teun.kweeter.getTopics
import java.time.LocalDateTime
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class TrendService {

    @Inject
    private lateinit var kweetService: KweetService

    fun getTrends(): Map<String, Int> {
        // TODO Stuff
        return emptyMap()
    }
}