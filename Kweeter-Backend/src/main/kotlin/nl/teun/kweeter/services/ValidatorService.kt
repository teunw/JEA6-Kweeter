package nl.teun.kweeter.services

import javax.ejb.Stateless

@Stateless
class ValidatorService {
    val usernameRegex = "[a-zA-Z0-9-_]+".toRegex()

    fun isUsernameValid(username : String) : Boolean {
        return usernameRegex.matches(username)
    }
}