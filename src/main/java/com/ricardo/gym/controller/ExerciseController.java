package com.ricardo.gym.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.gym.service.ExerciseService;

import lombok.RequiredArgsConstructor;

@RequestMapping("api/v1/exercises")
@RestController
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService service;

    private static final Logger loggerExerciseController = LoggerFactory.getLogger(ExerciseController.class);

    @GetMapping()
    public ResponseEntity<?> listExercises(){

        
        return null;
    }

}
