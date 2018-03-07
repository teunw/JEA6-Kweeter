package nl.teun.kweeter.controllers

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import nl.teun.kweeter.services.search.KweeterSearchService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/test")
class TestController {

    @Inject
    private lateinit var searchService: KweeterSearchService

    @GET
    @Path("/")
    fun getTest(): Response? {
        return Response.ok(Jwts.parser()
                .setSigningKey(MacProvider.generateKey(SignatureAlgorithm.HS512, MacProvider.DEFAULT_SECURE_RANDOM))
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXVuLndpbGxlbXNAbGl2ZS5ubCJ9.NpvKc8qDGIClTCWrsbSQ-pn9MtxP4Atb_J0Q3GwspBzgFtY6sEILc6foTDvbSOmMJvSbh7rOpcKqru2SxWP5dw").body.subject).build()
    }
}