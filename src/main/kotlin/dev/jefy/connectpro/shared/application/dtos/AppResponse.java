package dev.jefy.connectpro.shared.application.dtos;

import java.time.Instant;

/**
 * @author Jôph Yamba
 */
public record AppResponse<T>(
        String message,
        T data,
        int status,
        Instant timestamp
) {
}
