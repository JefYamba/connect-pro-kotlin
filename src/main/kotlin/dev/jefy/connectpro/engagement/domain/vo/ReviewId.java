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
public record ReviewId(
        @Column(name = "reviewer_id")
        UUID reviewerId,
        @Column(name = "service_id")
        UUID serviceId
) implements DIdentifier<ReviewId> {
    public ReviewId {
        Assert.notNull(reviewerId, "reviewerId cannot be null");
        Assert.notNull(serviceId, "serviceId cannot be null");
    }
    public static ReviewId of(UserId reviewerId, ServiceId serviceId) {
        return new ReviewId(reviewerId.value(), serviceId.value());
    }

    @Override
    public ReviewId value() {
        return this;
    }
}