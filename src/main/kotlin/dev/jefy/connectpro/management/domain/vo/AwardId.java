package dev.jefy.connectpro.management.domain.vo;

import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record AwardId(UUID value) implements DIdentifier<UUID> {
    public AwardId {
        Assert.notNull(value, "value cannot be null");
    }

    public static AwardId generate(){
        return new AwardId(UUID.randomUUID());
    }

    public static AwardId of(UUID awardId) {
        return new  AwardId(awardId);
    }
}

