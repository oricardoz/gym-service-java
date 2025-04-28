package com.ricardo.gym.dto;

import jakarta.validation.constraints.NotEmpty;

public record RegisterRequestDTO(
    @NotEmpty(message = "Name cannot be null")
    String name,
    @NotEmpty(message = "Password cannot be null")
    String password,
    @NotEmpty(message = "Email cannot be null")
    String email
) {

}
