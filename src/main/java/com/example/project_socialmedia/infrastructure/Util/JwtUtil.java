package com.example.project_socialmedia.infrastructure.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Use SHA-256
    private static final Long EXPIRATION_TIME = 864_000_000L; // 1 day

    // TODO: Add admin role ?
    // Encrypted Function
    public static String generateToken(String username, String email, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SECRET_KEY)
                .compact();
    }

    // Decrypted Function
    public static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)  // This part return Claims Object
                .getBody();
    }

    // Validation Function >> For Controller Action (Add, Update, Delete)
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            // TODO: Can i return this to something else like an Object of [String, Boolean] perhaps ?
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
