package nl.teun.kweeter.services

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import nl.teun.kweeter.domain.AuthToken
import nl.teun.kweeter.domain.Profile
import org.omg.CosNaming.NamingContextPackage.NotFound
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException
import javax.persistence.PersistenceContext
import javax.ws.rs.NotFoundException

@Stateless
class AuthServiceImpl : AuthService {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Throws(EntityNotFoundException::class)
    override fun findAuthTokenByProfile(profile: Profile): List<AuthToken> {
        return this.entityManager
                .createNamedQuery("authToken.forprofile")
                .setParameter("profileId", profile.id)
                .resultList
                .filterIsInstance<AuthToken>()
    }

    @Throws(EntityNotFoundException::class)
    override fun findAuthToken(token: String): AuthToken {
        val tokens = this.entityManager
                .createNamedQuery("authToken.fortoken")
                .setParameter("token", token)
                .resultList
                .filterIsInstance<AuthToken>()
        if (tokens.size > 1) {
            throw Exception("Found more than 1 token")
        }
        if (tokens.isEmpty()) {
            throw NotFoundException("Token not found")
        }
        return tokens.first()
    }

    override fun insertAuthToken(token: AuthToken) {
        this.entityManager.persist(token)
    }

    override fun deleteProfileAuh(token: AuthToken) {
        this.entityManager.remove(token)
    }

}