package com.ricardo.gym.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.gym.dto.WorkoutResponseDTO;
import com.ricardo.gym.service.WorkoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService service;

    @GetMapping
    public ResponseEntity<List<WorkoutResponseDTO>> getAllWorkout() {

        service.getAllWorkout();

        return null;
    }

}
