package dev.jefy.connectpro.engagement.applicaion.dtos;

import java.time.Instant;
import java.util.UUID;

import dev.jefy.connectpro.engagement.domain.Review;

/**
 * @author Jôph Yamba
 */
public record ReviewResponse(
        UUID reviewerId,
        UUID serviceId,
        int rating,
        String comment,
        Instant createdAt
) {
    public static ReviewResponse fromDomain(Review review) {
        return new ReviewResponse(
                review.getId().serviceId(),
                review.getId().reviewerId(),
                review.getRating().value(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}

