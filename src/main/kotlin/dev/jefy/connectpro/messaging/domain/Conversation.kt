package dev.jefy.connectpro.messaging.domain

import dev.jefy.connectpro.messaging.domain.vo.ConversationId
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.*
import java.time.Instant

/**
 * @author Jôph Yamba
 */
@Entity
@Table(name = "conversations")
open class Conversation(participantA: UserId, participantB: UserId) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "value"))
    var id: ConversationId = ConversationId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "participiant_a"))
    var participantA: UserId = participantA
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "participiant_b"))
    var participantB: UserId = participantB
        protected set

    var createdAt: Instant = Instant.now()
        protected set

    var lastModifiedAt: Instant? = null
        protected set
    
    
    fun setUpdated() { this.lastModifiedAt = Instant.now() }
}