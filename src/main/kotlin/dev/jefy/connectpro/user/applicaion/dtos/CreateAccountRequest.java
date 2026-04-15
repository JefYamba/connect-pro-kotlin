package dev.jefy.connectpro.user.applicaion.dtos;


import org.jspecify.annotations.NullMarked;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Jôph Yamba
 */

@NullMarked
public record CreateAccountRequest(
        @NotBlank(message = "name cannot be empty")
        String name,
        @Email(message = "email is not valid email address")
        @NotBlank(message = "email cannot be empty")
        String email,
        @NotBlank(message = "password cannot be empty")
        String password,
        @NotBlank(message = "confirm value cannot be empty")
        String confirmPassword
) {}

