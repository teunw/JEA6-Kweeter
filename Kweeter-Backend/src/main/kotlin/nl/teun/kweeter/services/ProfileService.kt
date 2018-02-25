package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Profile

interface ProfileService {

    fun findAll(maxResults: Int, offsetResults: Int): List<Profile>

    fun findById(id : Long) : Profile

    fun findByEmail(email : String) : Profile

    fun updateProfile(profile: Profile)

    fun createProfile(profile : Profile)

}