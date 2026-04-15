package dev.jefy.connectpro.user.applicaion.dtos;


/**
 * @author Jôph Yamba
 */
public record ChangePasswordRequest(
        String currentPassword,
        String newPassword,
        String confirmPassword
) {}
