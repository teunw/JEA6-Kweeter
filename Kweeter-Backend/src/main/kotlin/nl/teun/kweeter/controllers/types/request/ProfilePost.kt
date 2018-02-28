package nl.teun.kweeter.controllers.types.request

import nl.teun.kweeter.domain.Profile

open class ProfilePost : Profile() {
    var password: String = ""
}