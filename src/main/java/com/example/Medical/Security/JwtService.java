package com.example.Medical.Security;

import com.example.Medical.model.Doctor;
import com.example.Medical.model.Pharmacist;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate token for Doctor
    public String generateToken(Doctor doctor) {
        return Jwts.builder()
                .setSubject(doctor.getEmail())
                .claim("role", "DOCTOR")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Generate token for Pharmacist
    public String generateToken(Pharmacist pharmacist) {
        return Jwts.builder()
                .setSubject(pharmacist.getEmail())
                .claim("role", "PHARMACIST")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Extract role from token
    public String extractRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    // Generic token validation for Doctor or Pharmacist
    public <T> boolean isTokenValid(String token, T user) {
        String email = extractEmail(token);
        String userEmail;

        if (user instanceof Doctor doctor) {
            userEmail = doctor.getEmail();
        } else if (user instanceof Pharmacist pharmacist) {
            userEmail = pharmacist.getEmail();
        } else {
            return false; // unknown type
        }

        return email.equals(userEmail) && !isTokenExpired(token);
    }

    // Check if token expired
    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }
}
