package dev.jefy.connectpro.engagement.domain.vo;

import org.springframework.util.Assert;

import dev.jefy.connectpro.shared.infrastructure.ddd.DValueObject;
import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record Rating(Integer value) implements DValueObject<Integer> {
    public Rating {
        Assert.notNull(value, "value cannot be null");
        Assert.isTrue(value >= 1 && value <= 5, "rating must be between 1 and 5");
    }

    public static Rating of(int rating) {
        return new  Rating(rating);
    }
}
