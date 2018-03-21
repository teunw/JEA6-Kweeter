package nl.teun.kweeter

import java.io.Serializable
import javax.enterprise.context.SessionScoped
import javax.inject.Named

@Named
@SessionScoped
class Hello : Serializable {
    var name: String = ""
    var message: String = ""

    fun createMessage() {
        message = "Hello, $name!"
    }
}