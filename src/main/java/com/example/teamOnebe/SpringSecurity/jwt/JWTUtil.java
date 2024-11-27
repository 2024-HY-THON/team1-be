package com.example.teamOnebe.SpringSecurity.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil { //0.12.3

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret)
    {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String generateJwt(String username, String role, Long expiredMs)
    {
        return Jwts.builder()
                .claim("username",username)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis())) //발행시간(현재)
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey) //암호화 진행
                .compact();
    }

    public String getUsernameFromToken(String token)
    {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username",String.class);
    }

    public String getRoleFromToken(String token)
    {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",String.class);
    }

    public boolean isExpired(String token)
    {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

}
