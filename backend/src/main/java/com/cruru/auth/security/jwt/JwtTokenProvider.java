package com.cruru.auth.security.jwt;

import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.auth.security.TokenProperties;
import com.cruru.auth.security.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final TokenProperties tokenProperties;

    @Override
    public String createToken(Map<String, Object> claims) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenProperties.expireLength());

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.valueOf(tokenProperties.algorithm()),
                        tokenProperties.secretKey().getBytes())
                .compact();
    }

    @Override
    public boolean isAlive(String token) throws IllegalTokenException {
        Claims claims = extractClaims(token);
        Date expiration = claims.getExpiration();
        Date now = new Date();
        return expiration.after(now);
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenProperties.secretKey().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalTokenException();
        }
    }

    @Override
    public String extractClaim(String token, String key) throws IllegalTokenException {
        Claims claims = extractClaims(token);
        return claims.get(key, String.class);
    }
}
