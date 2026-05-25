package dev.jefy.connectpro.chats.application


import dev.jefy.connectpro.chats.application.dtos.ContactData
import dev.jefy.connectpro.chats.application.dtos.IncomingMessagePayload
import dev.jefy.connectpro.chats.application.dtos.MessageResponse
import dev.jefy.connectpro.chats.application.dtos.ReadReceiptPayload
import dev.jefy.connectpro.chats.domain.Conversation
import dev.jefy.connectpro.chats.domain.Message
import dev.jefy.connectpro.chats.domain.repositoty.ConversationRepository
import dev.jefy.connectpro.chats.domain.repositoty.MessageRepository
import dev.jefy.connectpro.chats.domain.vo.ConversationId
import dev.jefy.connectpro.chats.domain.vo.ReceiverId
import dev.jefy.connectpro.chats.domain.vo.SenderId
import dev.jefy.connectpro.shared.infrastructure.annotations.CommandService
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.util.*

/**
 * @author Jôph Yamba
 *
 * Toutes les opérations d'écriture sur le module messaging.
 * Après persist, notifie le destinataire via STOMP sur /user/{receiverId}/queue/messages
 */
@CommandService
class ChatCommandService(
    private val conversationRepository: ConversationRepository,
    private val messageRepository: MessageRepository,
    private val userClient: UserClient,
    private val messagingTemplate: SimpMessagingTemplate,
) {

    // Crée une conversation si elle n'existe pas déjà
    fun getOrCreateConversation(currentUserId: UUID, participantId: UUID): UUID {
        val existing = conversationRepository.findByParticipants(currentUserId, participantId)
        if (existing != null) return existing.id.value

        val conversation = Conversation(
            participantA = UserId.of(currentUserId),
            participantB = UserId.of(participantId),
        )
        return conversationRepository.save(conversation).id.value
    }

    // Envoie un message, met à jour la conversation, notifie via STOMP
    fun sendMessage(
        conversationId: UUID,
        senderId: UUID,
        receiverId: UUID,
        content: String,
    ): MessageResponse {
        val conversation = conversationRepository.findById(ConversationId(conversationId))
            .orElseThrow { IllegalArgumentException("Conversation $conversationId not found") }

        val message = Message(
            conversationId = conversation.id,
            senderId = SenderId(senderId),
            receiverId = ReceiverId(receiverId),
            content = content,
        )
        val saved = messageRepository.save(message)
        conversation.setUpdated()
        conversationRepository.save(conversation)

        val senderUser = userClient.getById(UserId(senderId))
        val receiverUser = userClient.getById(UserId.of(receiverId))
        val response = MessageResponse(
            id = saved.id.value,
            conversationId = conversationId,
            sender = ContactData(
                id = senderUser.id,
                name = senderUser.name,
                image = senderUser.image,
            ),
            content = saved.content,
            sentAt = saved.sentAt,
            isRead = false,
            isMine = false, // du point de vue du destinataire
        )

        // Notifie le destinataire en temps réel via STOMP
        messagingTemplate.convertAndSendToUser(
            receiverUser.email,
            "/queue/messages",
            IncomingMessagePayload(
                conversationId = conversationId,
                message = response,
            ),
        )

        // Retourne la réponse avec isMine=true pour l'émetteur
        return response.copy(isMine = true)
    }

    // Marque tous les messages non lus d'une conversation comme lus
    fun markAsRead(conversationId: UUID, currentUserId: UUID) {
        messageRepository.markAllAsRead(conversationId = conversationId, userId = currentUserId)

        // Trouve les expéditeurs des messages non lus pour les notifier
        // On notifie l'autre participant de la conversation
        val conversation = conversationRepository.findById(ConversationId(conversationId))
            .orElse(null) ?: return

        val otherParticipantId = if (conversation.participantA.value == currentUserId) {
            conversation.participantB.value
        } else {
            conversation.participantA.value
        }

        val otherUser = userClient.getById(UserId.of(otherParticipantId))

        // Notifie l'expéditeur que ses messages ont été lus
        messagingTemplate.convertAndSendToUser(
            otherUser.email,             // ← email pour le routing STOMP
            "/queue/read-receipts",
            ReadReceiptPayload(
                conversationId = conversationId,
                readByUserId = currentUserId,
            ),
        )
    }
}