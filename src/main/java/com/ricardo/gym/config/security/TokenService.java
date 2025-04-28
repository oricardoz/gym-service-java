package com.ricardo.gym.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ricardo.gym.model.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String tokenJWT = JWT.create()
                        .withIssuer("login-auth-api")
                        .withSubject(user.getEmail())
                        .withExpiresAt(this.generateExpirationDate())
                        .sign(algorithm);

            return tokenJWT;

        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }

    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String userEmail = JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

            return userEmail;
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    
}
