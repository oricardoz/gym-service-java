package com.ricardo.gym.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ricardo.gym.dto.WorkoutCreateRequestDTO;
import com.ricardo.gym.dto.WorkoutResponseDTO;
import com.ricardo.gym.exception.UserNotFoundException;
import com.ricardo.gym.model.Exercise;
import com.ricardo.gym.model.User;
import com.ricardo.gym.model.WorkoutExercise;
import com.ricardo.gym.model.WorkoutPlan;
import com.ricardo.gym.repository.ExerciseRepository;
import com.ricardo.gym.repository.UserRepository;
import com.ricardo.gym.repository.WorkoutExerciseRepository;
import com.ricardo.gym.repository.WorkoutPlanRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public List<WorkoutResponseDTO> getAllWorkout(){
        
        return workoutPlanRepository.findAll()
        .stream()
        .map(WorkoutResponseDTO::new)
        .collect(Collectors.toList());
    }

    @Transactional
    public Long createWorkout(WorkoutCreateRequestDTO dto, User instructor) {

        User student = userRepository.findById(dto.studentId())
            .orElseThrow(() -> new UserNotFoundException("Student not found"));

        WorkoutPlan workoutPlan = WorkoutPlan.builder()
            .title(dto.title())
            .student(student)
            .instructor(instructor)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusWeeks(4)) 
            .build();

        workoutPlanRepository.save(workoutPlan);

        List<WorkoutExercise> exercises = dto.exercises().stream()
            .map(exDto -> {
                Exercise exercise = exerciseRepository.findById(exDto.exerciseId())
                    .orElseThrow(() -> new RuntimeException("Exercise not found"));

                return WorkoutExercise.builder()
                    .exercise(exercise)
                    .workoutPlan(workoutPlan)
                    .sets(exDto.sets())
                    .repetitions(exDto.repetition())
                    .weight(exDto.weight())
                    .build();
            }).collect(Collectors.toList());

        workoutExerciseRepository.saveAll(exercises);

        workoutPlan.setExercises(exercises);
        var createdWorkout = workoutPlanRepository.save(workoutPlan);
        return createdWorkout.getId();
    }

    public WorkoutResponseDTO findWorkoutById(int id) {

        WorkoutPlan workout = workoutPlanRepository.findById((long) id).orElseThrow(() ->
                new RuntimeException("Workout not found"));

        return new WorkoutResponseDTO(workout);
    }
}
