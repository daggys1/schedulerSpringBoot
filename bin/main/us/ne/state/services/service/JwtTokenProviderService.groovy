package us.ne.state.services.service

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm

import java.security.SecureRandom

class JwtTokenProviderService {

    private String jwtIssuerID
    private String jwtIssuer
    private String jwtSecret

    JwtTokenProviderService(String jwtIssuerID, String jwtIssuer, String jwtSecret) {
        this.jwtIssuerID = jwtIssuerID
        this.jwtIssuer = jwtIssuer
        this.jwtSecret = jwtSecret
    }
// want to know more details read https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-25
    def token(def user) {


        def ttlMillis = 200000 //TODO externalize to props
        def jwtToken

        JwtBuilder token

        try {
            SecureRandom random = new SecureRandom()

            token = Jwts.builder()
                    .setIssuer(jwtIssuer)
                    .setId(jwtIssuerID)
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .setExpiration(getExpireyTime(ttlMillis))
                    .setSubject(user as String)

            jwtToken = token.compact()
        } catch (final Throwable t) {
            throw t
        }
        jwtToken
    }

    def getExpireyTime = { def ttlMillis ->
        Date exp
        long nowMillis = System.currentTimeMillis()
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis
            exp = new Date(expMillis)
        }
        exp
    }

}
