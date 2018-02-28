package nl.teun.kweeter.controllers.types.response

import nl.teun.kweeter.domain.Kweet

data class KweetLikeResponse (
    val liked: Boolean,
    val kweet : Kweet
)