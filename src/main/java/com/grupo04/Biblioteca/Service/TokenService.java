package com.grupo04.Biblioteca.Service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String gerarToken(String usuario) {
        SecretKey key = new SecretKeySpec(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
        return Jwts.builder()
                .setSubject(usuario) // usuário dono do token
                .claim("scope", "USER")
                .setIssuedAt(new Date()) // data de criação
                .setExpiration(new Date(System.currentTimeMillis() + 60000)) // expira em 24h
                .signWith(key)
                .compact();
    }
}