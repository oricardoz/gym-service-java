package com.ricardo.gym.config.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ricardo.gym.model.User;
import com.ricardo.gym.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    TokenService service;

    @Autowired
    UserRepository repository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String token = this.recoverTokenJWT(request);
        
        if (token != null) {
            String userEmail = service.validateToken(token);    
            configureUserAuthentication(userEmail);
            logger.info("User authenticated successfully: {} , accessing the url: {}", userEmail, request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }

    private String recoverTokenJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || authorizationHeader.isEmpty()){
            return null;
        }

        return authorizationHeader.replace("Bearer ", "");
    }

    private void configureUserAuthentication(String email){
        User user = repository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User Not Found"));

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthoritys());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
