package dev.jefy.connectpro.user.domain.vo;

import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record UserId(UUID value) implements DIdentifier<UUID> {
    public UserId {
        Assert.notNull(value, "value cannot be null");
    }

    public static UserId generate() {
        return new  UserId(UUID.randomUUID());
    }

    public static UserId of(UUID id) {
        return new  UserId(id);
    }
}
