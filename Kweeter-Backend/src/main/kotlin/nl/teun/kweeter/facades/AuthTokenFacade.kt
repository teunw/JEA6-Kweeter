package nl.teun.kweeter.facades

import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.toProfileFacade
import java.time.format.DateTimeFormatter

data class AuthTokenFacade(private val authToken: AuthToken = AuthToken()) {
    val id = authToken.id
    val token = authToken.token
    val profile = authToken.profile?.toProfileFacade()
    val issueDate = authToken.issueDate.format(DateTimeFormatter.ISO_DATE_TIME)
    val experiationDate = authToken.experationDate.format(DateTimeFormatter.ISO_DATE_TIME)
}