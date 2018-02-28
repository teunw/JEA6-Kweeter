package nl.teun.kweeter.controllers.types.request

import nl.teun.kweeter.domain.Kweet

open class KweetPost : Kweet() {
    var profileId: String = ""
}