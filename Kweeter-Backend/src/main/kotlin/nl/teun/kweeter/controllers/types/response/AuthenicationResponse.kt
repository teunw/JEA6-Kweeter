package nl.teun.kweeter.controllers.types.response

import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.domain.Profile

class AuthenicationResponse {
    var profile: Profile? = null
    var authToken: AuthToken? = null
}