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
public class JwtTokenProvider implements TokenProvider {

    private static final String ROLE = "role";
    private static final String EMAIL = "email";

    private final TokenProperties tokenProperties;

    @Override
    public String createToken(Member member) {
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

    @Override
    public boolean isExpired(String token) throws IllegalTokenException {
        try {
            Claims claims = extractClaims(token);
            Date expiration = claims.getExpiration();
            Date now = new Date();
            return expiration.before(now);
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalTokenException();
        }
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
    public String extractPayload(String token, String key) throws IllegalTokenException {
        Claims claims = extractClaims(token);
        return claims.get(key, String.class);
    }
}
