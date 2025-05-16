package com.ricardo.gym.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.gym.dto.LoginRequestDTO;
import com.ricardo.gym.dto.LoginResponseDTO;
import com.ricardo.gym.dto.RegisterRequestDTO;
import com.ricardo.gym.dto.RegisterResponseDTO;
import com.ricardo.gym.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequestMapping("api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req){
        
        LoginResponseDTO resp = this.service.login(req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO req) {

        RegisterResponseDTO resp = this.service.register(req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

}
