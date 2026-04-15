package dev.jefy.connectpro.user.applicaion.dtos;


import org.jspecify.annotations.NullMarked;

import java.util.UUID;

/**
 * @author Jôph Yamba
 */
@NullMarked
public record ValidateResetPasswordOtpRequest(UUID tokenId, int code) {}
