package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.KweetFacade
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

interface KweetService {

    fun findAll(maxResults: Int, offsetResults: Int): List<Kweet>

    @Throws(EntityNotFoundException::class)
    fun findById(id: Long): Kweet

    fun findByPublicId(id: String): Kweet

    @Throws(EntityNotFoundException::class)
    fun findByProfile(profile: Profile): List<Kweet>

    fun updateKweet(kweet: Kweet)

    fun createKweet(kweet: Kweet)

    fun recreateFromFacade(kweetFacade: KweetFacade): Kweet

    fun deleteKweet(kweet:Kweet)

    fun getKweetsAfter(dateTime: LocalDateTime): List<Kweet>

}