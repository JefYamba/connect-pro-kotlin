package dev.jefy.connectpro.messaging.domain;


import org.springframework.util.Assert;

import java.time.Instant;

import dev.jefy.connectpro.messaging.domain.vo.ConversationId;
import dev.jefy.connectpro.messaging.domain.vo.MessageId;
import dev.jefy.connectpro.messaging.domain.vo.ReceiverId;
import dev.jefy.connectpro.messaging.domain.vo.SenderId;
import dev.jefy.connectpro.shared.infrastructure.ddd.DAggregateRoot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "messages")
public class Message implements DAggregateRoot<MessageId> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "value"))
    private MessageId id;


    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "conversation_id"))
    private ConversationId conversationId;


    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sender_id"))
    private SenderId senderId;


    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "receiver_id"))
    private ReceiverId receiverId;
    
    private String content;
    
    private Instant sentAt;

    public Message(ConversationId conversationId, SenderId senderId, ReceiverId receiverId, String content) {
        Assert.notNull(conversationId, "conversationId is required");
        Assert.notNull(senderId, "senderId is required");
        Assert.notNull(receiverId, "receiverId is required");
        Assert.notNull(content, "content is required");
        this.id = MessageId.generate();
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.sentAt = Instant.now();
    }
}
