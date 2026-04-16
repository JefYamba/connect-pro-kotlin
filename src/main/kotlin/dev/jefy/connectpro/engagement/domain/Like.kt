package dev.jefy.connectpro.engagement.domain;


import org.springframework.util.Assert;

import java.time.Instant;

import dev.jefy.connectpro.engagement.domain.vo.LikeId;
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
@Table(name = "likes")
public class Like implements DAggregateRoot<LikeId> {
    @EmbeddedId
    @AttributeOverride(name = "value",column = @Column(name = "id"))
    private LikeId id;
    
    private Instant createdAt;

    public Like(UserId reviewerId, ServiceId serviceId) {
        Assert.notNull(reviewerId, "reviewerId cannot be null");
        Assert.notNull(serviceId, "serviceId cannot be null");
        this.id = LikeId.of(reviewerId, serviceId);
        this.createdAt = Instant.now();
    }
}
