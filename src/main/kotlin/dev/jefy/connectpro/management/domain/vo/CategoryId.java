package dev.jefy.connectpro.management.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record CategoryId(UUID value) implements DIdentifier<UUID> {
    public CategoryId {
        Assert.notNull(value, "value cannot be null");
    }

    public static CategoryId generate(){
        return new CategoryId(UUID.randomUUID());
    }

    public static CategoryId of(UUID value) {
        return new  CategoryId(value);
    }
}