package dev.jefy.connectpro.messaging.domain;


import org.springframework.util.Assert;

import java.time.Instant;

import dev.jefy.connectpro.messaging.domain.vo.ConversationId;
import dev.jefy.connectpro.shared.infrastructure.ddd.DAggregateRoot;
import dev.jefy.connectpro.user.domain.vo.UserId;
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
@Table(name = "conversations")
public class Conversation implements DAggregateRoot<ConversationId> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "value"))
    private ConversationId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "participiant_a"))
    private UserId participantA;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "participiant_b"))
    private UserId participantB;
    
    private Instant createdAt;
    
    private Instant lastModifiedAt;

    public Conversation(UserId participantA, UserId participantB) {
        Assert.isTrue(participantA != null && participantB != null, "participantA and participantB cannot be null!");
        this.id = ConversationId.generate();
        this.participantA = participantA;
        this.participantB = participantB;
        this.createdAt = Instant.now();
        this.lastModifiedAt = null;
    }
}
