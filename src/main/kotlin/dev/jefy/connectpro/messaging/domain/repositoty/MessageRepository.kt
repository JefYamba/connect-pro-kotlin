package dev.jefy.connectpro.messaging.domain.repositoty

import dev.jefy.connectpro.messaging.domain.Message
import dev.jefy.connectpro.messaging.domain.vo.MessageId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Jôph Yamba
 */
@Repository
interface MessageRepository : JpaRepository<Message, MessageId>
