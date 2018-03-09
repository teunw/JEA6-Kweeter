package nl.teun.kweeter.authentication.annotations

import javax.ws.rs.NameBinding

@NameBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class KweeterAuthRequired