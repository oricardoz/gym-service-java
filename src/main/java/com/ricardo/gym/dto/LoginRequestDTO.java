package com.ricardo.gym.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDTO(
    @NotEmpty(message = "Email cannot be null")
    String email,
    @NotEmpty(message = "Password cannot be null")
    String password
) {

}
