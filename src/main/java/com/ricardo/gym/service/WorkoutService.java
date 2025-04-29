package com.ricardo.gym.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ricardo.gym.dto.WorkoutResponseDTO;
import com.ricardo.gym.repository.WorkoutPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutPlanRepository repository;

    public List<WorkoutResponseDTO> getAllWorkout(){
        
        return repository.findAll()
        .stream()
        .map(workout -> 
            new WorkoutResponseDTO(workout))
        .collect(Collectors.toList());
    }

}
