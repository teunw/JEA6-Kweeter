package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.toProfileFacade

data class AuthTokenFacade(private val authToken: AuthToken = AuthToken()) {
    val token = authToken.token
    val profile = authToken.profile?.toProfileFacade()
    val issueDate = authToken.issueDate.toLocalDateTime()
    val experiationDate = authToken.experationDate.toLocalDateTime()
}