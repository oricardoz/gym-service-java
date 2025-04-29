package com.ricardo.gym.dto;

import com.ricardo.gym.model.enums.ECategoryExercise;
import jakarta.validation.constraints.NotBlank;

public record ExerciseCreateRequestDTO(
    @NotBlank(message = "Description cannot be blank")
    String description,
    @NotBlank(message = "Name cannot be blank")
    String name,
    @NotBlank(message = "Category cannot be blank")
    String category
) {

    public void validCategory() {
        if (this.category == null || this.category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be blank");
        }

        try {
            ECategoryExercise.valueOf(this.category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Category");
        }
    }
}