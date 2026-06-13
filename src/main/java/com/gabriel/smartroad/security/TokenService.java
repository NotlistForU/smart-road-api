package com.gabriel.smartroad.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gabriel.smartroad.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    // Senha secreta da API. Futuramente ela ficará no application.properties
    // Em busca do MVP vai ser deixado aki uma senha forte.
    @Value("${api.security.token.secret:minha-senha-secreta-muito-forte-123}")
    private String secret;

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("smartroad-api") // O nome da API
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o e-mail que estava guardado no token
        } catch (JWTVerificationException exception) {
            return ""; // Se o token for falso, expirado ou zoado, retorna vazio
        }
    }

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("smartroad-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-04:00"))) // Apenas Para o MVP do TCC futuramente ajustar!
                    .sign(algorithm);
        }catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating jwt token "+ exception);
        }
    }
}
