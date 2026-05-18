package dev.jefy.connectpro.chats.application

import dev.jefy.connectpro.chats.application.dtos.ContactData
import dev.jefy.connectpro.chats.application.dtos.ConversationResponse
import dev.jefy.connectpro.chats.application.dtos.MessageResponse
import dev.jefy.connectpro.chats.domain.Conversation
import dev.jefy.connectpro.chats.domain.Message
import dev.jefy.connectpro.chats.domain.repositoty.ConversationRepository
import dev.jefy.connectpro.chats.domain.repositoty.MessageRepository
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author Jôph Yamba
 *
 * Toutes les opérations de lecture sur le module messaging.
 * UserClient.getById(id) retourne un objet avec au moins id, name, image.
 */
@Service
@Transactional(readOnly = true)
class ChatQueryService(
    private val conversationRepository: ConversationRepository,
    private val messageRepository: MessageRepository,
    private val userClient: UserClient,
) {

    // Toutes les conversations du user courant avec aperçu du dernier message
    fun getConversations(currentUserId: UUID): List<ConversationResponse> {
        return conversationRepository.findAllByUserId(currentUserId).map { conversation ->
            val contactId = getContactId(conversation, currentUserId)
            val contactUser = userClient.getById(UserId(contactId))
            val lastMessage = messageRepository.findLastByConversationId(conversation.id.value)
            val unreadCount = messageRepository.countUnreadByConversationIdAndReceiverId(
                conversationId = conversation.id.value,
                userId = currentUserId,
            )

            ConversationResponse(
                id = conversation.id.value,
                contact = ContactData(
                    id = contactUser.id,
                    name = contactUser.name,
                    image = contactUser.image,
                ),
                lastMessage = lastMessage?.toResponse(currentUserId),
                unreadCount = unreadCount,
                createdAt = conversation.createdAt,
                lastModifiedAt = conversation.lastModifiedAt,
            )
        }
    }

    // Tous les messages d'une conversation (paginer si besoin plus tard)
    fun getMessages(conversationId: UUID, currentUserId: UUID): List<MessageResponse> {
        return messageRepository.findByConversationId(conversationId).map { message ->
            message.toResponse(currentUserId)
        }
    }

    // Rechercher parmi les conversations existantes par nom du contact
    fun searchConversations(currentUserId: UUID, query: String): List<ConversationResponse> {
        if (query.isBlank()) return emptyList()
        return getConversations(currentUserId).filter { conversation ->
            conversation.contact.name.contains(query, ignoreCase = true)
        }
    }

    // ── Helpers ─────────────────────────────────────────────────────────────

    private fun getContactId(conversation: Conversation, currentUserId: UUID): UUID {
        return if (conversation.participantA.value == currentUserId) {
            conversation.participantB.value
        } else {
            conversation.participantA.value
        }
    }

    private fun Message.toResponse(currentUserId: UUID): MessageResponse {
        val senderUser = userClient.getById(UserId(this.senderId.value))
        return MessageResponse(
            id = this.id.value,
            conversationId = this.conversationId.value,
            sender = ContactData(
                id = senderUser.id,
                name = senderUser.name,
                image = senderUser.image,
            ),
            content = this.content,
            sentAt = this.sentAt,
            isRead = this.isRead,
            isMine = this.senderId.value == currentUserId,
        )
    }
}