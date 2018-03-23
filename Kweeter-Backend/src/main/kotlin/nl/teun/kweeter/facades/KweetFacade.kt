package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.toProfileFacade
import java.time.format.DateTimeFormatter

data class KweetFacade(private val kweet: Kweet = Kweet()) {
    val publicId: String? = kweet.publicId
    val textContent: String? = kweet.textContent
    var author = kweet.author?.toProfileFacade()
    val likedBy = kweet.likedBy.map { it.toProfileFacade() }
    val date = kweet.date.format(DateTimeFormatter.ISO_DATE_TIME)
}
