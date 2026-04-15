package dev.jefy.connectpro.messaging.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record SenderId (UUID value) implements DIdentifier<UUID> {
    public SenderId {
        Assert.notNull(value, "value is required");
    }
}

