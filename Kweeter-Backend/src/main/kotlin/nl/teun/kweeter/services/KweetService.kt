package nl.teun.kweeter.services

import nl.teun.kweeter.domain.Kweet
import nl.teun.kweeter.domain.Profile
import nl.teun.kweeter.facades.KweetFacade
import java.time.LocalDateTime
import javax.persistence.EntityNotFoundException

abstract class KweetService {

    abstract fun findAll(maxResults: Int, offsetResults: Int): List<Kweet>

    @Throws(EntityNotFoundException::class)
    abstract fun findById(id: Long): Kweet

    abstract fun findByPublicId(id: String): Kweet

    @Throws(EntityNotFoundException::class)
    abstract fun findByProfile(profile: Profile): List<Kweet>

    abstract fun updateKweet(kweet: Kweet)

    abstract fun createKweet(kweet: Kweet)

    open fun recreateFromFacade(kweetFacade: KweetFacade) = this.findByPublicId(kweetFacade.publicId!!)

    abstract fun deleteKweet(kweet: Kweet)

    abstract fun getKweetsAfter(dateTime: LocalDateTime): List<Kweet>

}