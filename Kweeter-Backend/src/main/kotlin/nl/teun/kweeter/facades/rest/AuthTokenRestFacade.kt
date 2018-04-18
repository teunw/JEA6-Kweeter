package nl.teun.kweeter.facades.rest

import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.facades.AuthTokenFacade
import nl.teun.kweeter.toHateoas

class AuthTokenRestFacade(authToken: AuthToken) : AuthTokenFacade(authToken) {
    val linkToProfile = authToken.profile?.toHateoas()
}