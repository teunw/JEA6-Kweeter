package nl.teun.kweeter.beans

import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.services.ProfileService
import java.io.Serializable
import javax.enterprise.context.SessionScoped
import javax.inject.Inject
import javax.inject.Named

@Named
@SessionScoped
class ProfileAdminBean : Serializable {
    @Inject
    private lateinit var profileService: ProfileService

    private var profiles = listOf<Profile>()

    fun getProfiles(): List<Profile> {
        if (this.profiles.isEmpty()) {
            this.profiles = this.profileService.findAll(0, 0)
        }
        return this.profiles
    }
}