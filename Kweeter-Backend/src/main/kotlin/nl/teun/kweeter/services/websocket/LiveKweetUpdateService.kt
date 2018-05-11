package nl.teun.kweeter.services.websocket

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import nl.teun.kweeter.facades.KweetFacade
import nl.teun.kweeter.toJson
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@ServerEndpoint("/update/kweets")
class LiveKweetUpdateService {

    private val sessions = mutableSetOf<Session>()

    @OnOpen
    fun onOpen(session: Session) {
        println("User connected")
        this.sessions.add(session)
    }

    @OnMessage
    fun sendNewKweet(message:String, session: Session) {
        // For validation purposes
        val messageAsObject = Gson().fromJson<KweetFacade>(message)
        this.sessions.forEach { it.basicRemote.sendText(messageAsObject.toJson()) }
    }

    @OnClose
    fun onClose(session: Session) {
        println("User disconnected")
        this.sessions.removeIf { it.id == session.id }
    }

}