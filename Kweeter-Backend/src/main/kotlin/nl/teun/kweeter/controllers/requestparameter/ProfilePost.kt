package nl.teun.kweeter.controllers.requestparameter

import nl.teun.kweeter.domain.Profile

open class ProfilePost() : Profile() {
    public var password: String = ""
}