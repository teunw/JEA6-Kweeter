package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import javax.persistence.EntityNotFoundException

interface KweetService {

    fun findAll(maxResults: Int, offsetResults: Int): List<Kweet>

    @Throws(EntityNotFoundException::class)
    fun findById(id : Long) : Kweet

    @Throws(EntityNotFoundException::class)
    fun findByProfile(profile : Profile) : List<Kweet>

    fun updateKweet(kweet: Kweet)

    fun createKweet(kweet: Kweet)

}