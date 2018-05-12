package nl.teun.kweeter.facades.ws

import nl.teun.kweeter.facades.UnsafeProfileFacade

data class WebsocketKweetFacade(
        val author: UnsafeProfileFacade,
        val textContent: String,
        val date: String
)