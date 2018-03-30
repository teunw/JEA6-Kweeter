package nl.teun.kweeter.beans

import nl.teun.kweeter.facades.KweetFacade
import nl.teun.kweeter.services.KweetService
import nl.teun.kweeter.toKweetFacade
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

    private var allKweets = mutableListOf<KweetFacade>()

    fun getKweets(): List<KweetFacade> {
        if (this.allKweets.isEmpty()) {
            this.allKweets.addAll(this.kweetService.findAll(50, 0).map { it.toKweetFacade() })
        }
        return this.allKweets
    }

    fun deleteKweet(kweet: KweetFacade) {
        this.kweetService.deleteKweet(this.kweetService.findByPublicId(kweet.publicId!!))
        this.allKweets.remove(kweet)
    }

    fun getKweetDate(kweet: KweetFacade) = kweet.date.format(DateTimeFormatter.ISO_DATE_TIME)
}