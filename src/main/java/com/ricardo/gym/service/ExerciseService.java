package com.ricardo.gym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ricardo.gym.dto.ExerciseCreateRequestDTO;
import com.ricardo.gym.dto.ExerciseResponseDTO;
import com.ricardo.gym.model.Exercise;
import com.ricardo.gym.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository repository;

    public List<ExerciseResponseDTO> getAllExercises() {
        
        return repository.findAll()
                .stream()
                .map( data -> 
                new ExerciseResponseDTO(
                    data.getName(),
                    data.getDescription(),
                    data.getCategory().name())
                    )
                .toList();
    }
    
    public ExerciseResponseDTO getExerciseById(Integer id){

        var response = repository.findById(id.longValue());

        return response.map(Exercise::toDTO).orElse(null);
    }

    public Long createExercise(ExerciseCreateRequestDTO req){

        req.validCategory();
        Exercise resp = repository.save(new Exercise(req));
        return resp.getId();
    }
}
