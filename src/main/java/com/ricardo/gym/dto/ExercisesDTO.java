package com.ricardo.gym.dto;

public record ExercisesDTO(
    Long exerciseId,
    Integer sets,
    Integer repetition, 
    Double weight
){

}

