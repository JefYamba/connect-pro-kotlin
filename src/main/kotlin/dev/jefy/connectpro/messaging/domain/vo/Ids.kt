package dev.jefy.connectpro.messaging.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record ConversationId (UUID value) implements DIdentifier<UUID> {
    public ConversationId {
        Assert.notNull(value, "value cannot be null");
    }
    public static  ConversationId generate() {
        return new ConversationId(UUID.randomUUID());
    }
}

