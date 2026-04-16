package dev.jefy.connectpro.recommandation.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record EventTrackingId (UUID value) implements DIdentifier<UUID> {
    public EventTrackingId {
        Assert.notNull(value, "value cannot be null");
    }
    public static EventTrackingId generate() {
        return new EventTrackingId(UUID.randomUUID());
    }
}
