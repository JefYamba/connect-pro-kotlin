package dev.jefy.connectpro.messaging.application.dtos

import java.time.Instant
import java.util.*


/**
 * @author Jôph Yamba
 */
// ── Request DTOs ────────────────────────────────────────────────────────────

data class SendMessageRequest(
    val receiverId: UUID,
    val content: String,
)

data class CreateConversationRequest(
    val participantId: UUID,
)

// ── Response DTOs ────────────────────────────────────────────────────────────

data class ContactData(
    val id: UUID,
    val name: String,
    val image: String?,
)

data class MessageResponse(
    val id: UUID,
    val conversationId: UUID,
    val sender: ContactData,
    val content: String,
    val sentAt: Instant,
    val isRead: Boolean,
    // true si le message a été envoyé par le currentUser
    val isMine: Boolean,
)

data class ConversationResponse(
    val id: UUID,
    val contact: ContactData,      // l'autre participant
    val lastMessage: MessageResponse?,
    val unreadCount: Long,
    val createdAt: Instant,
    val lastModifiedAt: Instant?,
)

// Payload envoyé via STOMP au destinataire
data class IncomingMessagePayload(
    val conversationId: UUID,
    val message: MessageResponse,
)


data class PresencePayload(
    val userId: UUID,
    val online: Boolean,
)
 