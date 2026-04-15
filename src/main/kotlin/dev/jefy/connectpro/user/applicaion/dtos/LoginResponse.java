package dev.jefy.connectpro.user.applicaion.dtos;


import org.jspecify.annotations.NullMarked;

/**
 * @author Jôph Yamba
 */
@NullMarked
public record LoginResponse(UserResponse user, String token) {}
