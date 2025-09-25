package com.example.foodordersystem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {
        // Secret key-in düzgün base64 formatda olduğuna əmin olun
        try {
            byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new RuntimeException("Invalid secret key format", e);
        }
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            // Token-dəki boşluq və xüsusi simvolları təmizləyin
            String cleanToken = token.trim();
            if (cleanToken.contains(" ")) {
                cleanToken = cleanToken.substring(cleanToken.lastIndexOf(" ") + 1);
            }

            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(cleanToken)
                    .getBody()
                    .getSubject();
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token format: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT claims string is empty: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JWT token: " + e.getMessage());
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            String cleanToken = token.trim();
            if (cleanToken.contains(" ")) {
                cleanToken = cleanToken.substring(cleanToken.lastIndexOf(" ") + 1);
            }

            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(cleanToken)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}