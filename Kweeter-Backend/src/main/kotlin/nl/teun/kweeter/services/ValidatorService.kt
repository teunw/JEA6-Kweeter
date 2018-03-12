package nl.teun.kweeter.services

interface ValidatorService {
    fun isUsernameValid(username : String) : Boolean
}