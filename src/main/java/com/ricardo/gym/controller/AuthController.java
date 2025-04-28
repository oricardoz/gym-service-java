package com.ricardo.gym.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.gym.dto.LoginRequestDTO;
import com.ricardo.gym.dto.LoginResponseDTO;
import com.ricardo.gym.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO req){
        
        LoginResponseDTO resp = this.service.login(req);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
