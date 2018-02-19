package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile

interface KweetService {

    fun findAll() : List<Kweet>

    fun findById(id : Long) : Kweet

    fun updateKweet(profile: Kweet)

    fun createKweet(profile : Kweet)

}