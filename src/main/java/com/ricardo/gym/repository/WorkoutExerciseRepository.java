package com.ricardo.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardo.gym.model.WorkoutExercise;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long>{

}
