package com.ricardo.gym.dto;

import java.util.List;

public record WorkoutCreateRequestDTO(
    Long studentId,
    String title,
    List<ExercisesDTO> exercises
) {

}

