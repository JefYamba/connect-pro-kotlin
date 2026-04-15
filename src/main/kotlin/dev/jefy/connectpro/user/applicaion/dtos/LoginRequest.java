package dev.jefy.connectpro.user.applicaion.dtos;

import org.jspecify.annotations.NullMarked;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Jôph Yamba
 */
@NullMarked
public record LoginRequest(
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password
) {}
