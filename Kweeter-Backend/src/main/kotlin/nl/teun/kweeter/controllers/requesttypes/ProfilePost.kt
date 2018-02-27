package nl.teun.kweeter.controllers.requesttypes

import nl.teun.kweeter.domain.Profile

open class ProfilePost() : Profile() {
    public var password: String = ""
}