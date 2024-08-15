package com.cruru.auth.security;

import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String ROLE = "role";
    private static final String EMAIL = "email";

    private final TokenProperties tokenProperties;

    public String create(Member member) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenProperties.expireLength());

        return Jwts.builder()
                .claim(ROLE, member.getRole().name())
                .claim(EMAIL, member.getEmail())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.valueOf(tokenProperties.algorithm()),
                        tokenProperties.secretKey().getBytes())
                .compact();
    }

    public String extractMemberEmail(String token) {
        Claims claims = extractClaims(token);
        return claims.get(EMAIL, String.class);
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

    public String extractMemberRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get(ROLE, String.class);
    }

    public boolean isExpired(String token) {
        Claims claims = extractClaims(token);
        Date expiration = claims.getExpiration();
        Date now = new Date();

        return expiration.before(now);
    }
}
