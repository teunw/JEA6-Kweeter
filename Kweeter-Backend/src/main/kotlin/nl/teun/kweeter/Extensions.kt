package nl.teun.kweeter

import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.AuthTokenFacade
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
fun Kweet.toKweetFacade() = nl.teun.kweeter.facades.KweetFacade(this)
fun AuthToken.toAuthFacade() = AuthTokenFacade(this)

fun httpResponseNotFound(): Response.ResponseBuilder {
    return Response.status(Response.Status.NOT_FOUND)
}

fun httpResponseBadRequest(): Response.ResponseBuilder {
    return Response.status(Response.Status.BAD_REQUEST)
}