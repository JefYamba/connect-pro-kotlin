package dev.jefy.connectpro.user.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record TokenId(UUID value) implements DIdentifier<UUID> {
    public TokenId {
        Assert.notNull(value, "TokenId cannot be null");
    }

    public static TokenId generate() {
        return new  TokenId(UUID.randomUUID());
    }

    public static TokenId of(UUID value) {
        return new  TokenId(value);
    }
}
