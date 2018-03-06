package nl.teun.kweeter.services

import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.domain.Profile

interface AuthService {

    fun findAuthTokenByProfile(profile: Profile): List<AuthToken>

    fun findAuthToken(token: String): AuthToken

    fun insertAuthToken(token: AuthToken)

    fun deleteProfileAuh(token: AuthToken)

}