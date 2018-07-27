package us.ne.state.services.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import us.ne.state.services.Exceptions.UnauthorizedUserException

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

/*class JwtFilter implements Filter {

    private String jwtSecret
    private String jwtIssuer
    private String jwtId

    JwtFilter(String jwtSecret, String jwtIssuer, String jwtId) {
        this.jwtSecret = jwtSecret
        this.jwtIssuer = jwtIssuer
        this.jwtId = jwtId
    }

    @Override
    void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request
        def jwt = httpRequest.getHeader('token')
        def user = httpRequest.getHeader('user')
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
        } catch (final ExpiredJwtException t) {
            def unAuthExp = new UnauthorizedUserException(t.message)
            throw unAuthExp
        } catch(final Throwable t){
            throw unAuthorized(t as UnauthorizedUserException) as Throwable
        }
    }

    @Override
    void destroy() {
    // nada..
    }

     static ResponseEntity<Map<String, Object>> unAuthorized(UnauthorizedUserException e) {
        def map = new HashMap(1)
        map.put('ERROR', ['Authorization': e.message])
        new ResponseEntity<Map<String, Map<String, String>>>(map, HttpStatus.UNAUTHORIZED)
    }
}*/
