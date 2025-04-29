package com.ricardo.gym.model;

import com.ricardo.gym.dto.ExerciseCreateRequestDTO;
import com.ricardo.gym.dto.ExerciseResponseDTO;
import com.ricardo.gym.model.enums.ECategoryExercise;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ECategoryExercise category;

    public ExerciseResponseDTO toDTO(){

        return new ExerciseResponseDTO(
            this.name,
            this.description,
            this.category.name()
        );
    }

    public Exercise(ExerciseCreateRequestDTO dto){
        this.name = dto.name();
        this.description = dto.description();
        this.category = ECategoryExercise.valueOf(dto.category());
    }

}
