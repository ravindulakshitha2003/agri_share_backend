
package com.example.suplier_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JWTService {

    private final SecretKey secretKey;

    public JWTService(
            @Value("${JWT_SECRET}") String secret) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {

        return getClaims(token).getSubject();
    }

    public String getRole(String token) {

        return getClaims(token).get("roles", String.class);
    }
}
