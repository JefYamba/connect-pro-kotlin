package dev.jefy.connectpro.chats.domain.repositoty


import dev.jefy.connectpro.chats.domain.Conversation
import dev.jefy.connectpro.chats.domain.vo.ConversationId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author Jôph Yamba
 */
@Repository
interface ConversationRepository : JpaRepository<Conversation, ConversationId> {

    // Toutes les conversations d'un user (participant A ou B), triées par dernière activité
    @Query("""
        SELECT c FROM Conversation c
        WHERE c.participantA.value = :userId OR c.participantB.value = :userId
        ORDER BY COALESCE(c.lastModifiedAt, c.createdAt) DESC
    """)
    fun findAllByUserId(@Param("userId") userId: UUID): List<Conversation>

    // Trouver une conversation existante entre deux users (dans les deux sens)
    @Query("""
        SELECT c FROM Conversation c
        WHERE (c.participantA.value = :userA AND c.participantB.value = :userB)
           OR (c.participantA.value = :userB AND c.participantB.value = :userA)
    """)
    fun findByParticipants(
        @Param("userA") userA: UUID,
        @Param("userB") userB: UUID,
    ): Conversation?
}

