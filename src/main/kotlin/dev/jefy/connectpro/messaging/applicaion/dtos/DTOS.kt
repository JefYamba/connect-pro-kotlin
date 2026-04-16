package dev.jefy.connectpro.messaging.applicaion.dtos

import dev.jefy.connectpro.user.application.dtos.UserResponse
import jakarta.validation.constraints.NotBlank
import java.time.Instant
import java.util.*


/**
 * @author Jôph Yamba
 */

data class ConversationRequest(val participant1: UUID, val participant2: UUID)

data class ConversationResponse(
    val id: UUID,
    val participantA: UserResponse,
    val participantB: UserResponse,
    val createdAt: Instant,
    val lastModifiedAt: Instant
)


data class MessageRequest(
    val conversationId:  UUID,
    val senderId:  UUID,
    val receiverId:  UUID,
    @field:NotBlank(message = "message content must not be empty")
    val content:  String?
)

data class MessageResponse(
    val id: UUID,
    val conversationId: UUID,
    val senderId: UserResponse,
    val receiverId: UserResponse,
    val content: String,
    val sentAt: Instant
)
