package nl.teun.kweeter

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun LocalDateTime.toJavaUtilDate(): Date {
    val instant = Instant.from(this.atZone(ZoneId.systemDefault()))
    return java.util.Date.from(instant)
}
