package com.ricardo.gym.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricardo.gym.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
