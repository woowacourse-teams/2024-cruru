package com.cruru.auth.security;

import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private static final String ROLE = "role";
    private static final String EMAIL = "email";

    @Value("${security.jwt.token.algorithm}")
    private String algorithm;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validTime;

    public String create(Member member) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validTime);

        return Jwts.builder()
                .claim(ROLE, member.getRole().name())
                .claim(EMAIL, member.getEmail())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.valueOf(algorithm), secretKey.getBytes())
                .compact();
    }

    public String extractMemberEmail(String token) {
        Claims claims = extractClaims(token);
        return (String) claims.get(EMAIL);
    }

    public String extractMemberRole(String token) {
        Claims claims = extractClaims(token);
        return (String) claims.get(ROLE);
    }

    public boolean isExpired(String token) {
        Claims claims = extractClaims(token);
        Date expiration = claims.getExpiration();
        Date now = new Date();

        return expiration.before(now);
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalTokenException();
        }
    }
}
