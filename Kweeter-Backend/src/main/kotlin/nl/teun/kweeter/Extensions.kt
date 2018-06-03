package nl.teun.kweeter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.domain.ProfileFollower
import nl.teun.kweeter.facades.AuthTokenFacade
import nl.teun.kweeter.facades.ProfileFacade
import nl.teun.kweeter.facades.ProfileFollowerFacade
import nl.teun.kweeter.facades.rest.KweetRestFacade
import nl.teun.kweeter.facades.rest.ProfileRestFacade
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.ws.rs.core.Response

fun LocalDateTime.toJavaUtilDate(): Date {
    val instant = Instant.from(this.atZone(ZoneId.systemDefault()))
    return java.util.Date.from(instant)
}

val ApiBaseUrl = "http://localhost:8080/Kweeter-Backend/api"

fun Profile.toProfileFacade() = ProfileFacade(this)
fun Profile.toProfileRestFacade() = ProfileRestFacade(this)
fun Profile.toHateoas() = "$ApiBaseUrl/profiles/${this.id}"

fun Kweet.toFacade() = nl.teun.kweeter.facades.KweetFacade(this)
fun Kweet.toRestFacade() = KweetRestFacade(this)


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
inline fun <reified T> Any.toJson(jsonSerializer: Gson) =
        GsonBuilder().registerTypeAdapter(T::class.java, jsonSerializer).create().toJson(this)

inline fun <reified T> Gson.fromJsonArray(s: String): List<T> {
    val listType = object : TypeToken<List<T>>() {

    }.type
    return this.fromJson(s, listType)
}

