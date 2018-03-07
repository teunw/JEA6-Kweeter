package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile
import javax.persistence.EntityNotFoundException

interface ProfileService {

    fun findAll(maxResults: Int = 50, offsetResults: Int = 0): List<Profile>

    @Throws(EntityNotFoundException::class)
    fun findById(id : Long) : Profile

    @Throws(EntityNotFoundException::class)
    fun findByEmail(email : String) : Profile

    fun updateProfile(profile: Profile)

    fun createProfile(profile : Profile)

}