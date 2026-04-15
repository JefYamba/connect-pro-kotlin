package dev.jefy.connectpro.engagement.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.portfolio.domain.vo.ServiceId;
import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;
import dev.jefy.connectpro.user.domain.vo.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */

@Embeddable
public record LikeId(
        @Column(name = "user_id")
        UUID userId,
        @Column(name = "service_id")
        UUID serviceId
) implements DIdentifier<LikeId> {
    public LikeId {
        Assert.notNull(userId, "reviewerId cannot be null");
        Assert.notNull(serviceId, "serviceId cannot be null");
    }
    public static LikeId of(UserId userId, ServiceId serviceId) {
        return new LikeId(userId.value(), serviceId.value());
    }

    @Override
    public LikeId value() {
        return this;
    }
}
