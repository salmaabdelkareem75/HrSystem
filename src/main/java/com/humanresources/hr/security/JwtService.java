package com.humanresources.hr.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "my-super-secret-key-for-hr-system-jwt-2026";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60;

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8)
            );

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> user = new HashMap<>();

        user.put("email", userDetails.getUsername());

        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority()
                        .replace("ROLE_", ""))
                .orElse(null);

        user.put("role", role);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("user", user)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {

        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {

        Claims claims = getClaims(token);

        Map<String, Object> user =
                claims.get("user", Map.class);

        if (user == null) {
            return null;
        }

        return (String) user.get("role");
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(
            String token,
            String email) {

        String tokenEmail = extractEmail(token);

        return tokenEmail.equals(email)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }
}