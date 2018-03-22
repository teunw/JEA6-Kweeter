package nl.teun.kweeter.beans

import nl.teun.kweeter.authentication.ProfileRole
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.ProfileService
import java.io.Serializable
import javax.enterprise.context.SessionScoped
import javax.inject.Named

@Named
@SessionScoped
class LoginBean : Serializable {

    private var loggedInProfile: Profile? = null

    private lateinit var profileService: ProfileService

    fun attemptLogin(profileId: Long, password: String): Boolean {
        val profile = this.profileService.findById(profileId)
        val loginResult = profile.checkPassword(password)
        if (loginResult) {
            this.loggedInProfile = profile
        }
        return loginResult
    }

    fun getRole(): ProfileRole = this.loggedInProfile?.role ?: ProfileRole.USER

}