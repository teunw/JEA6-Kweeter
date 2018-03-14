package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.toProfileFacade

data class KweetFacade(private val kweet: Kweet = Kweet()) {
    val publicId = kweet.publicId
    val textContent = kweet.textContent
    val author = kweet.author.toProfileFacade()
    val likedBy = kweet.likedBy.map { it.toProfileFacade() }
    val date = kweet.date
}
