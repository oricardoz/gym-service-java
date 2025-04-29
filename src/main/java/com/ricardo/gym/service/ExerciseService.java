package com.ricardo.gym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ricardo.gym.model.Exercise;
import com.ricardo.gym.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository repository;

    public List<Exercise> listAllExercises() {
        
        return repository.findAll();
    }
    
}
