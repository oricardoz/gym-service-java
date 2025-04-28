package com.ricardo.gym.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ricardo.gym.config.security.TokenService;
import com.ricardo.gym.dto.LoginRequestDTO;
import com.ricardo.gym.dto.LoginResponseDTO;
import com.ricardo.gym.dto.RegisterRequestDTO;
import com.ricardo.gym.dto.RegisterResponseDTO;
import com.ricardo.gym.model.User;
import com.ricardo.gym.model.enums.EUserType;
import com.ricardo.gym.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEnconder;
    private final TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO req){
        
        User user = this.repository.findByEmail(req.email())
                            .orElseThrow(() -> new RuntimeException("Wrong credentials"));

        if(!passwordEnconder.matches(req.password(), user.getPassword())){
            throw new RuntimeException("Wrong credentials");
        }
        
        String tokenJWT = this.tokenService.generateToken(user);
        return new LoginResponseDTO(tokenJWT);
    }

    public RegisterResponseDTO register(RegisterRequestDTO req){
        
        User user = this.repository.findByEmail(req.email()).orElse(null);

        if(user != null){
            throw new RuntimeException("There is already a user with that email");
        }

        user = User.builder()
                .email(req.email())
                .userRole(EUserType.STUDENT)
                .password(passwordEnconder.encode(req.password()))
                .build();

        this.repository.save(user);    

        String tokenJWT = this.tokenService.generateToken(user);
        return new RegisterResponseDTO(tokenJWT);
    }
}
