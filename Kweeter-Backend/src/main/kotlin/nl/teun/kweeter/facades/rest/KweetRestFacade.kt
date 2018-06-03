package nl.teun.kweeter.facades.rest

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.facades.KweetFacade
import nl.teun.kweeter.toHateoas

class KweetRestFacade(kweet: Kweet) : KweetFacade(kweet) {
    val profileLink = kweet.author?.toHateoas()
}