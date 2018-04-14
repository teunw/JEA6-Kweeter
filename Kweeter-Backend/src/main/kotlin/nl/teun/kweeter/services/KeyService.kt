package nl.teun.kweeter.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import nl.teun.kweeter.domain.Profile
import javax.ejb.Stateless

@Stateless
class KeyService {

    private val KweeterJwtKey = MacProvider.generateKey(SignatureAlgorithm.HS512, MacProvider.DEFAULT_SECURE_RANDOM)

    fun getNewKey(profile: Profile) =
        Jwts.builder()
                .setSubject(profile.email.toString())
                .signWith(SignatureAlgorithm.HS512, ByteArray(64))
                .compact()

    fun getKeyAsString(compactJws:String) =
            Jwts.parser().setSigningKey(ByteArray(64)).parseClaimsJws(compactJws).body.subject

}