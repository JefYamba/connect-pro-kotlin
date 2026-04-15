package dev.jefy.connectpro.messaging.applicaion.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * @author Jôph Yamba
 */
public record MessageRequest (
        @NotNull(message = "conversation value is required")
        UUID conversationId,
        @NotNull(message = "sender value is required")
        UUID senderId,
        @NotNull(message = "receiver value is required")
        UUID receiverId,
        @NotNull(message = "content is required")
        String content
){}

