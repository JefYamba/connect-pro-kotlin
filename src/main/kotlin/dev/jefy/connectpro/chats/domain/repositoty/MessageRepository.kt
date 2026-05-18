package dev.jefy.connectpro.chats.domain.repositoty

import dev.jefy.connectpro.chats.domain.Message
import dev.jefy.connectpro.chats.domain.vo.MessageId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author Jôph Yamba
 */
@Repository
interface MessageRepository : JpaRepository<Message, MessageId> {
    // Messages d'une conversation, triés chronologiquement
    @Query("""
        SELECT m FROM Message m
        WHERE m.conversationId.value = :conversationId
        ORDER BY m.sentAt ASC
    """)
    fun findByConversationId(@Param("conversationId") conversationId: UUID): List<Message>

    // Dernier message d'une conversation (pour l'aperçu dans la liste)
    @Query("""
        SELECT m FROM Message m
        WHERE m.conversationId.value = :conversationId
        ORDER BY m.sentAt DESC
        LIMIT 1
    """)
    fun findLastByConversationId(@Param("conversationId") conversationId: UUID): Message?

    // Nombre de messages non lus pour un receiver dans une conversation
    @Query("""
        SELECT COUNT(m) FROM Message m
        WHERE m.conversationId.value = :conversationId
          AND m.receiverId.value = :userId
          AND m.isRead = false
    """)
    fun countUnreadByConversationIdAndReceiverId(
        @Param("conversationId") conversationId: UUID,
        @Param("userId") userId: UUID,
    ): Long

    // Marquer tous les messages non lus d'une conversation comme lus
    @Modifying
    @Query("""
        UPDATE Message m SET m.isRead = true
        WHERE m.conversationId.value = :conversationId
          AND m.receiverId.value = :userId
          AND m.isRead = false
    """)
    fun markAllAsRead(
        @Param("conversationId") conversationId: UUID,
        @Param("userId") userId: UUID,
    )
}
