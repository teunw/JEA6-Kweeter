package nl.teun.kweeter.services

import nl.teun.kweeter.domain.ProfileAuth

interface ProfileAuthService {

    fun findById(id: Long): ProfileAuth

    fun findByToken(token: String): List<ProfileAuth>

    fun update(p:ProfileAuth)

    fun create(p:ProfileAuth)

}