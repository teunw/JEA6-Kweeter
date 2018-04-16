package nl.teun.kweeter

import com.google.gson.Gson
import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.domain.ProfileFollower
import nl.teun.kweeter.facades.AuthTokenFacade
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.facades.ProfileFollowerFacade
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.ws.rs.core.Response

fun LocalDateTime.toJavaUtilDate(): Date {
    val instant = Instant.from(this.atZone(ZoneId.systemDefault()))
    return java.util.Date.from(instant)
}

val ApplicationRoot = "http://localhost:8080/Kweeter-Backend/api"

fun Profile.toProfileFacade() = ProfileFacade(this)
fun Profile.toProfileUrl() = "$ApplicationRoot/profiles/${this.id}"
fun Kweet.toKweetFacade() = nl.teun.kweeter.facades.KweetFacade(this)
fun Kweet.toKweetUrl() = "$ApplicationRoot/kweets/${this.publicId}"
fun AuthToken.toAuthFacade() = AuthTokenFacade(this)
fun ProfileFollower.toFollowerFacade() = ProfileFollowerFacade(this)

fun httpResponseNotFound(): Response.ResponseBuilder {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
}

fun httpResponseBadRequest(): Response.ResponseBuilder {
    return Response.status(Response.Status.BAD_REQUEST)
}

fun Kweet.getTopics() = "#[a-zA-Z0-9]+".toRegex().findAll(this.textContent.subSequence(0, Int.MAX_VALUE)).map { it.value }

fun Any.toJson() = Gson().toJson(this)
