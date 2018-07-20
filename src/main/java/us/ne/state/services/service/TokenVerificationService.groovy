package us.ne.state.services.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureException
import us.ne.state.services.Exceptions.UnauthorizedUserException


class TokenVerificationService {

    private String jwtSecret
    private String jwtIssuer
    private String jwtId

    TokenVerificationService(String jwtSecret,
                             String jwtIssuer,
                             String jwtId) {
        if (null == jwtSecret || null == jwtIssuer || null == jwtId)
            throw new IllegalArgumentException("cannot instantiate ${getClass()} with out providing required params.")
        this.jwtSecret = jwtSecret
        this.jwtIssuer = jwtIssuer
        this.jwtId = jwtId
    }

    def verifyJwtOrUnauthorizedUserException(def jwt, def user) {
        Claims claims
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt as String).getBody()
            if (!claims.getIssuer().equalsIgnoreCase(jwtIssuer)) {
                def unAuthExp = new UnauthorizedUserException('JWT not issued by the recognized party')
                throw unAuthExp
            } else if (!claims.getSubject().equalsIgnoreCase(user as String)) {
                def unAuthExp = new UnauthorizedUserException('JWT not issued for the user currently accessing')
                throw unAuthExp
            } else if (!claims.getId().equalsIgnoreCase(jwtId)) {
                def unAuthExp = new UnauthorizedUserException('JWTID not recognized')
                throw unAuthExp
            }
        } catch (final SignatureException se) {
            def unAuthExp = new UnauthorizedUserException(se.message)
            throw unAuthExp
        } catch (final Throwable t) {
            throw t
        }
    }

}
