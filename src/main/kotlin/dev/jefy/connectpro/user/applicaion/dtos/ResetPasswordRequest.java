package dev.jefy.connectpro.user.applicaion.dtos;


import java.util.UUID;

/**
 * @author Jôph Yamba
 */

public record ResetPasswordRequest(
        UUID tokenId,
        String newPassword,
        String confirmPassword
) {}


