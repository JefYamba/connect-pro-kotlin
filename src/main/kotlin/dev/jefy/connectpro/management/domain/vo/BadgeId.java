package dev.jefy.connectpro.management.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;
import jakarta.validation.constraints.NotNull;

/**
 * @author Jôph Yamba
 */
public record BadgeId(UUID value) implements DIdentifier<UUID> {
    public BadgeId {
        Assert.notNull(value, "value cannot be null");
    }

    public static BadgeId generate(){
        return new BadgeId(UUID.randomUUID());
    }

    public static BadgeId of(@NotNull UUID badgeId) {
        return new  BadgeId(badgeId);
    }
}
