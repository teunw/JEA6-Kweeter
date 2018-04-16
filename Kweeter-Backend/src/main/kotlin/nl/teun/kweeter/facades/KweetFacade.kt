package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.toProfileUrl
import java.time.format.DateTimeFormatter

data class KweetFacade(private val kweet: Kweet = Kweet()) {
    val publicId: String? = kweet.publicId
    val textContent: String? = kweet.textContent
    var author = kweet.author?.toProfileUrl()
    val likedBy = kweet.likedBy.map { it.toProfileUrl() }
    val date = kweet.date.format(DateTimeFormatter.ISO_DATE_TIME)
}
