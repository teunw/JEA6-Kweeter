package nl.teun.kweeter.beans

import nl.teun.kweeter.authentication.ProfileRole
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.ProfileService
import java.io.Serializable
import javax.enterprise.context.SessionScoped
import javax.faces.component.html.HtmlInputText
import javax.inject.Named

@Named
@SessionScoped
class LoginBean : Serializable {

    private var loggedInProfile: Profile? = null

    private lateinit var profileService: ProfileService

    var username: HtmlInputText = HtmlInputText()
    var password: HtmlInputText = HtmlInputText()

    init {
        this.username.value = ""
        this.password.value = ""
    }

    fun attemptLogin(): Boolean {
        val profile = this.profileService.findByUsername(this.username.value.toString())
        val loginResult = profile.checkPassword(this.password.value.toString())
        if (loginResult) {
            this.loggedInProfile = profile
        }
        return loginResult
    }

    fun getRole(): ProfileRole = this.loggedInProfile?.role ?: ProfileRole.USER

}