package nl.teun.kweeter.controllers.requestTypes

import nl.teun.kweeter.domain.Kweet

open class KweetPost():Kweet() {
    public var profileId: String = ""
}