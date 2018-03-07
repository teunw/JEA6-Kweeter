package nl.teun.kweeter.controllers.types.request

data class AuthenticationRequest(
        val email: String? = "",
        val password: String? = ""
)