package dev.jefy.connectpro.messaging.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record MessageId (UUID value) implements DIdentifier<UUID> {
    public MessageId {
        Assert.notNull(value, "value cannot be null");
    }
    public static  MessageId generate() {
        return new MessageId(UUID.randomUUID());
    }
}

