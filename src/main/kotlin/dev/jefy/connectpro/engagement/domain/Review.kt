package dev.jefy.connectpro.engagement.domain;

import org.springframework.util.Assert;

import java.time.Instant;

import dev.jefy.connectpro.engagement.domain.vo.Rating;
import dev.jefy.connectpro.engagement.domain.vo.ReviewId;
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
@Table(name = "reviews")
public class Review implements DAggregateRoot<ReviewId> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private ReviewId id;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "rating"))
    private Rating rating;
    
    @Column(nullable = false, updatable = false)
    private String comment;
    
    private Instant createdAt;

    public Review(UserId reviewerId, ServiceId serviceId, Rating rating, String comment) {
        Assert.notNull(reviewerId, "reviewer Id cannot be null");
        Assert.notNull(serviceId, "service Id cannot be null");
        Assert.notNull(rating, "rating cannot be null");
        Assert.hasText(comment, "comment cannot be empty");
        this.id = ReviewId.of(reviewerId, serviceId);
        this.rating = rating;
        this.comment = comment;
        this.createdAt = Instant.now();
    }

    public void update(Rating rating, String comment) {
        Assert.notNull(rating, "rating cannot be null");
        Assert.hasText(comment, "comment cannot be empty");

        this.rating = rating;
        this.comment = comment;
    }
}
