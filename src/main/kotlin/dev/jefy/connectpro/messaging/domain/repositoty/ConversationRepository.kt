package dev.jefy.connectpro.messaging.domain.repositoty


import dev.jefy.connectpro.messaging.domain.Conversation
import dev.jefy.connectpro.messaging.domain.vo.ConversationId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Jôph Yamba
 */
@Repository
interface ConversationRepository : JpaRepository<Conversation, ConversationId>

