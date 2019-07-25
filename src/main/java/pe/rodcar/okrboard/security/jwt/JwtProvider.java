package pe.rodcar.okrboard.security.jwt;

import io.jsonwebtoken.*;
import pe.rodcar.okrboard.security.services.UserPrinciple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${farma.app.jwtSecret}")
    private String jwtSecret;

    @Value("${farma.app.jwtExpiration}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
		                .setSubject((userPrincipal.getUsername()))
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
		                .signWith(SignatureAlgorithm.HS512, jwtSecret)
		                .compact();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Token JWT inválido -> Mensaje: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido -> Mensaje: {} ", e);
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado -> Mensaje: {} ", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT no soportado -> Mensaje: {} ", e);
        } catch (IllegalArgumentException e) {
            logger.error("La cadena de notificaciones JWT está vacía -> Mensaje: {}", e);
        }
        
        return false;
    }
    
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
			                .setSigningKey(jwtSecret)
			                .parseClaimsJws(token)
			                .getBody().getSubject();
    }
}