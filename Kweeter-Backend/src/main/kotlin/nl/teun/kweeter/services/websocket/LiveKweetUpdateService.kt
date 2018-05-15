package nl.teun.kweeter.services.websocket

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import nl.teun.kweeter.facades.ws.WebsocketKweetFacade
import nl.teun.kweeter.toJson
import javax.enterprise.context.ApplicationScoped
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@ApplicationScoped
@ServerEndpoint("/update/kweets")
class LiveKweetUpdateService {

    @OnOpen
    fun onOpen(session: Session) {
        println("User connected")
        Companion.allAppSessions.add(session)
        println("${Companion.allAppSessions.size} sessions active")
    }

    @OnMessage
    fun sendNewKweet(message:String, session: Session) {
        // For validation purposes
        println("Got message $message")
        val messageAsObject = Gson().fromJson<WebsocketKweetFacade>(message)
        Companion.allAppSessions.forEach { it.basicRemote.sendText(messageAsObject.toJson()) }
    }

    @OnClose
    fun onClose(session: Session) {
        println("User disconnected")
        Companion.allAppSessions.removeIf { it.id == session.id }
    }

    companion object {
        val allAppSessions = mutableSetOf<Session>()
    }

}