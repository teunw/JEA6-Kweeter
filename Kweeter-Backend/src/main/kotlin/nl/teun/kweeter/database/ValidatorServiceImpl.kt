package nl.teun.kweeter.services

import javax.ejb.Stateless

@Stateless
class ValidatorServiceImpl : ValidatorService {
    val usernameRegex = "[a-zA-Z0-9-_]{6,64}".toRegex()

    override fun isUsernameValid(username : String) : Boolean {
        var count = 0
        for (i in usernameRegex.findAll(username).iterator()) {
            count += 1
        }
        return count == 1
    }
}