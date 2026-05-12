package dev.jefy.connectpro.messaging.domain

import dev.jefy.connectpro.messaging.domain.vo.ConversationId
import dev.jefy.connectpro.messaging.domain.vo.MessageId
import dev.jefy.connectpro.messaging.domain.vo.ReceiverId
import dev.jefy.connectpro.messaging.domain.vo.SenderId
import jakarta.persistence.*
import java.time.Instant

/**
 * @author Jôph Yamba
 */
@Entity
@Table(name = "messages")
open class Message(
    conversationId: ConversationId,
    senderId: SenderId,
    receiverId: ReceiverId,
    content: String,
) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: MessageId = MessageId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "conversation_id"))
    var conversationId: ConversationId = conversationId
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "sender_id"))
    var senderId: SenderId = senderId
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "receiver_id"))
    var receiverId: ReceiverId = receiverId
        protected set

    var content: String = content
        protected set

    var sentAt: Instant = Instant.now()
        protected set

    var isRead: Boolean = false
        protected set

    init {
        require(content.isNotBlank()) { "Message content must not be blank" }
    }

    fun markAsRead() {
        this.isRead = true
    }
}

/*
package dev.jefy.connectpro.messaging.domain

import dev.jefy.connectpro.messaging.domain.vo.ConversationId
import dev.jefy.connectpro.messaging.domain.vo.MessageId
import dev.jefy.connectpro.messaging.domain.vo.ReceiverId
import dev.jefy.connectpro.messaging.domain.vo.SenderId
import jakarta.persistence.*
import java.time.Instant

*/
/**
 * @author Jôph Yamba
 *//*

@Entity
@Table(name = "messages")
open class Message(conversationId: ConversationId, senderId: SenderId, receiverId: ReceiverId, content: String) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: MessageId = MessageId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "conversation_id"))
    var conversationId: ConversationId = conversationId
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "sender_id"))
    var senderId: SenderId = senderId
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "receiver_id"))
    var receiverId: ReceiverId = receiverId
        protected set

    var content: String = content
        protected set

    var sentAt: Instant = Instant.now()
        protected set
    
    init {
        require(content.isNotBlank()) { "Message content must not be blank" }
    }
}*/
