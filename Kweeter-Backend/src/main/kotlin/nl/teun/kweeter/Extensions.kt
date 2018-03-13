package nl.teun.kweeter

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.KweetFacade
import nl.teun.kweeter.facades.ProfileFacade
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.ws.rs.core.Response

fun LocalDateTime.toJavaUtilDate(): Date {
    val instant = Instant.from(this.atZone(ZoneId.systemDefault()))
    return java.util.Date.from(instant)
}

fun Profile.toProfileFacade() = ProfileFacade(this)
fun ProfileFacade.toProfile() = Profile(this)
fun Kweet.toKweetFacade() = nl.teun.kweeter.facades.KweetFacade(this)
fun KweetFacade.toKweet() = nl.teun.kweeter.domain.Kweet(this)
fun List<Kweet>.toKweetFacades() = this.map { it.toKweetFacade() }
fun List<KweetFacade>.toKweets() = this.map { it.toKweet() }

fun httpResponseNotFound(): Response.ResponseBuilder {
    return Response.status(Response.Status.NOT_FOUND)
}

fun httpResponseBadRequest(): Response.ResponseBuilder {
    return Response.status(Response.Status.BAD_REQUEST)
}