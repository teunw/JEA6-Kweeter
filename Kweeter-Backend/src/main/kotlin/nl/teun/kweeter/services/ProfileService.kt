package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile

interface ProfileService {

    fun findAll(maxResults: Int = 50, offsetResults: Int = 0): List<Profile>

    fun findById(id : Long) : Profile

    fun findByEmail(email : String) : Profile

    fun updateProfile(profile: Profile)

    fun createProfile(profile : Profile)

}