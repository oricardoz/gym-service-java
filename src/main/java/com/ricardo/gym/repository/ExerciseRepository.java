package com.ricardo.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardo.gym.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
