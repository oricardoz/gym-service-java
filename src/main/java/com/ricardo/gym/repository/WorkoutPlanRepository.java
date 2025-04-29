package com.ricardo.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardo.gym.model.WorkoutPlan;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long>{

}
