package nl.teun.kweeter.controllers.types.response

import nl.teun.kweeter.facades.KweetFacade

data class KweetLikeResponse (
        val liked: Boolean,
        val kweet: KweetFacade
)