package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.Kweet

data class KweetFacade(val kweet: Kweet) {
    val publicId = kweet.publicId
    val textContent = kweet.textContent
    val author = ProfileFacade(kweet.author)
    val likedBy = kweet.likedBy.map { ProfileFacade(it) }
    val date = kweet.date
}
