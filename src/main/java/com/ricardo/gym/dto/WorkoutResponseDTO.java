package com.ricardo.gym.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ricardo.gym.model.User;
import com.ricardo.gym.model.WorkoutPlan;

public record WorkoutResponseDTO(
        Long id,
        User student,
        User instructor,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        List<WorkoutExercises> exercises
) {
    public WorkoutResponseDTO(WorkoutPlan workout){
        this(
                workout.getId(),
                workout.getStudent(),
                workout.getInstructor(),
                workout.getCreatedAt(),
                workout.getExpiresAt(),
                workout.getExercises().stream()
                .map( ex -> new WorkoutExercises(
                    ex.getExercise().getName(),
                    ex.getExercise().getCategory().name(),
                    ex.getSets(),
                    ex.getRepetitions(),
                    ex.getWeight()
                ))
                .toList()
        );
    }
}

record WorkoutExercises(
    String name, 
    String category,
    Integer sets,
    Integer repetitions,
    Double weigth
){

   
}