package nl.teun.kweeter.services

import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.domain.Profile
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.ws.rs.NotFoundException

@Stateless
class AuthServiceImpl : AuthService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findAuthTokenByProfile(profile: Profile): List<AuthToken> {
        return this.entityManager
                .createNamedQuery("authToken.forprofile")
                .setParameter(":profileId", profile.id)
                .resultList
                .filterIsInstance<AuthToken>()
    }

    override fun findAuthToken(token: String): AuthToken {
        val tokens = this.entityManager
                .createNamedQuery("authToken.fortoken")
                .setParameter(":token", token)
                .resultList
                .filterIsInstance<AuthToken>()
        if (token.length > 1) {
            throw Exception("Found more than 1 token")
        }
        if (token.isEmpty()) {
            throw NotFoundException("Token not found")
        }
        return tokens[0]
    }

    override fun insertAuthToken(token: AuthToken) {
        this.entityManager.persist(token)
    }

    override fun deleteProfileAuh(token: AuthToken) {
        this.entityManager.remove(token)
    }

}