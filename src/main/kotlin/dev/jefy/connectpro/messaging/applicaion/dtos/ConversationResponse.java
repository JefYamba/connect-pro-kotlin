package dev.jefy.connectpro.messaging.applicaion.dtos;

import java.time.Instant;
import java.util.UUID;

import dev.jefy.connectpro.user.application.dtos.UserResponse;

/**
 * @author Jôph Yamba
 */
public record ConversationResponse (
        UUID id,
        UserResponse participantA,
        UserResponse participantB,
        Instant createdAt,
        Instant lastModifiedAt
){}
