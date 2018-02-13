package nl.teun.kweeter.services

import nl.teun.kweeter.domain.ProfileAuth
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
open class ProfileAuthServiceImpl : ProfileAuthService {

    @PersistenceContext
    private lateinit var em: EntityManager

    override fun findById(id: Long): ProfileAuth {
        val possibleProfiles = this.em
                .createNamedQuery("ProfileAuth.id")
                .setParameter(":id", id)
                .resultList
                .filterIsInstance<ProfileAuth>()
                .toList()
        if (possibleProfiles.size != 1) {
            throw Exception("Found invalid number of auths (should be 1), found ${possibleProfiles.size} for id $id")
        }
        return possibleProfiles.first()
    }

    override fun findByToken(token: String): List<ProfileAuth> {
        return this.em
                .createNamedQuery("ProfileAuth.token")
                .setParameter(":token", token)
                .resultList
                .filterIsInstance<ProfileAuth>()
                .toList()
    }

    override fun update(p: ProfileAuth) {
        this.em.merge(p)
    }

    override fun create(p: ProfileAuth) {
        this.em.persist(p)
    }
}