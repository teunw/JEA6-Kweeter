package nl.teun.kweeter.controllers.requestparameter

import nl.teun.kweeter.domain.Kweet

open class KweetPost : Kweet() {
    var profileId: String = ""
}