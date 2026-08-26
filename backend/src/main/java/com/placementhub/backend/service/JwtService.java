package com.placementhub.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;


    // Create signing key
    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    // Generate JWT
    public String generateToken(
            String email,
            String role
    ) {

        return Jwts.builder()

                .subject(email)

                .claim("role", role)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expirationTime
                        )
                )

                .signWith(getSigningKey())

                .compact();
    }


    // Extract email
    public String extractEmail(String token) {

        return getClaims(token)
                .getSubject();
    }


    // Extract role
    public String extractRole(String token) {

        return getClaims(token)
                .get("role", String.class);
    }


    // Validate JWT
    public boolean isTokenValid(String token) {

        try {

            Claims claims =
                    getClaims(token);

            return claims
                    .getExpiration()
                    .after(new Date());

        } catch (Exception e) {

            return false;
        }
    }


    // Extract claims
    private Claims getClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
}