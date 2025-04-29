package com.ricardo.gym.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.gym.dto.ExerciseCreateRequestDTO;
import com.ricardo.gym.dto.ExerciseResponseDTO;
import com.ricardo.gym.service.ExerciseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("api/v1/exercises")
@RestController
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService service;

    @GetMapping()
    public ResponseEntity<List<ExerciseResponseDTO>> getExercises(){

        return ResponseEntity.status(HttpStatus.OK).body(service.getAllExercises());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> getExerciseById(@PathVariable int id){

        return ResponseEntity.status(HttpStatus.OK).body(service.getExerciseById(id));
    }

    @PostMapping()
    public ResponseEntity<?> createExercise(@Valid @RequestBody ExerciseCreateRequestDTO req) {
        Long id = service.createExercise(req);
        return ResponseEntity
            .created(URI.create("/api/v1/exercises/" + id))
            .build();
    }

}
