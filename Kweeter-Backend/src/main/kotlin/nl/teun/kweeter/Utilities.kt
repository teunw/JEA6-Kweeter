package nl.teun.kweeter

import java.time.LocalDateTime
import java.util.*

class Utilities {
    companion object {
        fun localDateTimeToJavaDate(ldate:LocalDateTime):Date {
            return ldate.toJavaUtilDate()
        }
    }
}

