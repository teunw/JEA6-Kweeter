package nl.teun.kweeter.authentication

import java.io.IOException
import javax.ejb.Stateful
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

import javax.security.auth.Subject
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.NameCallback
import javax.security.auth.callback.PasswordCallback
import javax.security.auth.callback.UnsupportedCallbackException
import javax.security.auth.login.FailedLoginException
import javax.security.auth.login.LoginException
import javax.security.auth.spi.LoginModule

@Named("login")
class KweeterLoginModule : LoginModule {
    private var subject: Subject? = null
    private var callbackHandler: CallbackHandler? = null
    private var sharedState: Map<*, *>? = null
    private var options: Map<*, *>? = null

    private var succeeded = false

    @Throws(LoginException::class)
    override fun abort(): Boolean {
        println("Login Module - abort called")
        return false
    }

    @Throws(LoginException::class)
    override fun commit(): Boolean {
        println("Login Module - commit called")
        return succeeded
    }

    override fun initialize(subject: Subject, callbackHandler: CallbackHandler, sharedState: Map<String, *>,
                            options: Map<String, *>) {

        println("Login Module - initialize called")
        this.subject = subject
        this.callbackHandler = callbackHandler
        this.sharedState = sharedState
        this.options = options

        println("testOption value: " + options["testOption"] as String)

        succeeded = false
    }

    @Throws(LoginException::class)
    override fun login(): Boolean {
        println("Login Module - login called")
        if (callbackHandler == null) {
            throw LoginException("Oops, callbackHandler is null")
        }

        val callbacks = arrayOfNulls<Callback>(2)
        callbacks[0] = NameCallback("Username:")
        callbacks[1] = PasswordCallback("Password:", false)

        try {
            callbackHandler!!.handle(callbacks)
        } catch (e: IOException) {
            throw LoginException("Oops, IOException calling handle on callbackHandler")
        } catch (e: UnsupportedCallbackException) {
            throw LoginException("Oops, UnsupportedCallbackException calling handle on callbackHandler")
        }

        val usernameCallback = callbacks[0] as NameCallback
        val passwordCallback = callbacks[1] as PasswordCallback

        val username = usernameCallback.name
        val password = String(passwordCallback.password)

        if (username == "username" && password == "password") {
            println("Success! You get to log in!")
            succeeded = true
            return succeeded
        } else {
            println("Failure! You don't get to log in")
            succeeded = false
            throw FailedLoginException("Sorry! No login for you.")
        }
    }

    @Throws(LoginException::class)
    override fun logout(): Boolean {
        println("Login Module - logout called")
        return false
    }
}