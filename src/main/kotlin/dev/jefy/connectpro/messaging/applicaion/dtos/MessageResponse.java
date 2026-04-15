package dev.jefy.connectpro.messaging.applicaion.dtos;

import java.time.Instant;
import java.util.UUID;

import dev.jefy.connectpro.user.application.dtos.UserResponse;

/**
 * @author Jôph Yamba
 */
public record MessageResponse (
        UUID id,
        UUID conversationId,
        UserResponse senderId,
        UserResponse receiverId,
        String content,
        Instant sentAt
){}
