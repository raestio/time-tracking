package cz.cvut.fit.timetracking.security.token.service.impl;

import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import cz.cvut.fit.timetracking.security.token.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${time-tracking.security.auth.jwt.encodedSecretKey}")
    private String encodedSecretKey;

    @Value("${time-tracking.security.auth.jwt.secretKeyAlgorithm}")
    private String secretKeyAlgorithm;

    @Value("${time-tracking.security.auth.jwt.tokenExpirationSec}")
    private Integer tokenExpirationSec;

    @Override
    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date expiryDate = getExpiryDate();
        String token = Jwts.builder()
                .setSubject(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
        return token;
    }

    @Override
    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return Integer.valueOf(claims.getSubject());
    }

    @Override
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            LOGGER.warn("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOGGER.warn("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.warn("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.warn("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.warn("JWT claims string is empty.");
        }
        return false;
    }

    private Key getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(encodedSecretKey);
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, secretKeyAlgorithm);
        return secretKey;
    }

    private Date getExpiryDate() {
        Date now = new Date();
        return new Date(now.getTime() + tokenExpirationSec * 1000);
    }
}
