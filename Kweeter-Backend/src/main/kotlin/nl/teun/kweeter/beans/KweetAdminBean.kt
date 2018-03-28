package nl.teun.kweeter.beans

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.services.KweetService
import java.io.Serializable
import java.time.format.DateTimeFormatter
import javax.annotation.security.RolesAllowed
import javax.enterprise.context.SessionScoped
import javax.inject.Inject
import javax.inject.Named

@Named
@SessionScoped
@RolesAllowed(value = ["Moderator", "Admin"])
class KweetAdminBean : Serializable {

    @Inject
    private lateinit var kweetService: KweetService

    private var allKweets = listOf<Kweet>()

    fun getKweets(): List<Kweet> {
        if (this.allKweets.isEmpty()) {
            this.allKweets = this.kweetService.findAll(0, 0)
        }
        return this.allKweets
    }

    fun deleteKweet(kweet:Kweet) = this.kweetService.deleteKweet(kweet)

    fun getKweetDate(kweet: Kweet) = kweet.date.format(DateTimeFormatter.ISO_DATE_TIME)
}