package com.ricardo.gym.controller;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.gym.dto.WorkoutCreateRequestDTO;
import com.ricardo.gym.dto.WorkoutResponseDTO;
import com.ricardo.gym.model.User;
import com.ricardo.gym.service.WorkoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService service;

    @GetMapping
    public ResponseEntity<List<WorkoutResponseDTO>> getAllWorkout() {

        return ResponseEntity.status(HttpStatus.OK).body(service.getAllWorkout());
    }

    @PostMapping
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutCreateRequestDTO req , @AuthenticationPrincipal User instructor) {

        Long id = service.createWorkout(req, instructor);
        return ResponseEntity
            .created(URI.create("/api/v1/workout/" + id))
            .build();
    }
}
