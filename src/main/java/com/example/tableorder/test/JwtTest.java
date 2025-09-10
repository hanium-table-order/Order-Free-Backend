package com.example.tableorder.test;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Collections;

public class JwtTest {
    public static void main(String[] args) {
        String secret = "yourVeryLongAndSecureSecretKeyAtLeast32BytesChangeThisInProd"; // .env JWT_SECRET와 동일
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        String token = Jwts.builder()
                .setSubject("test-owner")
                .claim("role", "OWNER")
                .setIssuer("tableorder-app")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1시간 유효
                .signWith(key)
                .compact();
        System.out.println("Bearer " + token);
    }
}