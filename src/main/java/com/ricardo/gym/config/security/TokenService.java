package com.ricardo.gym.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ricardo.gym.exception.TokenCreationException;
import com.ricardo.gym.exception.TokenValidationException;
import com.ricardo.gym.model.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Instant expiration = this.generateExpirationDate();

            String token = JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiration)
                    .sign(algorithm);

            logger.info("JWT token successfully generated for user {}. Expires at {}", user.getEmail(), expiration);

            return token;
        } catch (JWTCreationException exception){
            logger.error("Failed to generate token for user {}: {}", user.getEmail(), exception.getMessage(), exception);
            throw new TokenCreationException("Error while creating token");
        }
    }

    public String validateToken(String token) {
        try {
            logger.debug("Validating JWT token: {}", token);

            Algorithm algorithm = Algorithm.HMAC256(secret);

            String subject = JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

            logger.info("JWT token successfully validated for subject: {}", subject);

            return subject;
        } catch (JWTVerificationException exception) {
            logger.warn("JWT token validation failed: {}", exception.getMessage(), exception);
            throw new TokenValidationException("Error while validating token");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
