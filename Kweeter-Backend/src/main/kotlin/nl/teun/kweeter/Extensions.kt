package nl.teun.kweeter

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.ws.rs.core.Response

fun LocalDateTime.toJavaUtilDate(): Date {
    val instant = Instant.from(this.atZone(ZoneId.systemDefault()))
    return java.util.Date.from(instant)
}

fun httpResponseNotFound(): Response.ResponseBuilder {
    return Response.status(Response.Status.NOT_FOUND)
}

fun httpResponseBadRequest(): Response.ResponseBuilder {
    return Response.status(Response.Status.BAD_REQUEST)
}