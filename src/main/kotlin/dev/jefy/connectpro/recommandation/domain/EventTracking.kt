package dev.jefy.connectpro.recommandation.domain;

import org.springframework.util.Assert;

import java.time.Instant;
import java.util.UUID;

import dev.jefy.connectpro.recommandation.domain.vo.EventTrackingId;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
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
@Table(name = "events_tracking")
public class EventTracking implements DAggregateRoot<EventTrackingId> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "value"))
    private EventTrackingId id;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private UUID targetId;
    @Enumerated(EnumType.STRING)
    private TargetType targetType;
    private Instant timestamp;

    public EventTracking(UserId userId, EventType eventType, UUID targetId, TargetType targetType) {
        Assert.notNull(userId, "reviewerId cannot be null");
        Assert.notNull(eventType, "EventType cannot be null");
        Assert.notNull(targetId, "TargetId cannot be null");
        Assert.notNull(targetType, "TargetType cannot be null");
        
        this.id = EventTrackingId.generate();
        this.userId = userId;
        this.eventType = eventType;
        this.targetId = targetId;
        this.targetType = targetType;
        this.timestamp = Instant.now();
    }
}

