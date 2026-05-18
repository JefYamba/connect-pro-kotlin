package dev.jefy.connectpro.chats.domain.vo

import jakarta.persistence.Embeddable
import java.util.*

/**
 * @author Jôph Yamba
 */

@Embeddable
data class ConversationId(var value: UUID) {
    companion object {
        fun generate(): ConversationId = ConversationId(UUID.randomUUID())
    }
}
@Embeddable
data class MessageId(var value: UUID) {
    companion object {
        fun generate(): MessageId = MessageId(UUID.randomUUID())
    }
}
@Embeddable
data class ReceiverId(var value: UUID){
    companion object {
        fun generate(): ReceiverId = ReceiverId(UUID.randomUUID())
    }
}
@Embeddable
data class SenderId(var value: UUID){
    companion object {
        fun generate(): SenderId = SenderId(UUID.randomUUID())
    }
}

