package com.virtualcareerfair.service;

import com.virtualcareerfair.dto.UserSummary;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String jwtSecret;
    private final long expirationMs;

    public JwtService(
            @Value("${JWT_SECRET:change-this-to-a-very-long-random-secret-key-at-least-32-bytes}") String jwtSecret,
            @Value("${JWT_EXPIRATION_MS:86400000}") long expirationMs) {
        this.jwtSecret = jwtSecret;
        this.expirationMs = expirationMs;
    }

    public String generateToken(UserSummary user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.id())
                .claim("name", user.name())
                .claim("email", user.email())
                .claim("role", user.role())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(signingKey())
                .compact();
    }

    public UserSummary parseUser(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return new UserSummary(
                claims.getSubject(),
                claims.get("name", String.class),
                claims.get("email", String.class),
                claims.get("role", String.class));
    }

    private SecretKey signingKey() {
        byte[] keyBytes;

        if (jwtSecret.matches("^[A-Za-z0-9+/=]+$") && jwtSecret.length() % 4 == 0) {
            try {
                keyBytes = Decoders.BASE64.decode(jwtSecret);
            } catch (IllegalArgumentException exception) {
                keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
            }
        } else {
            keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
