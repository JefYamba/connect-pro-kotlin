package dev.jefy.connectpro.portfolio.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record JobPostId (UUID value) implements DIdentifier<UUID> {
    public JobPostId {
        Assert.notNull(value, "value cannot be null");
    }

    public static JobPostId generate() {
        return new  JobPostId(UUID.randomUUID());
    }
    
    public static JobPostId of(UUID value) {
        return new JobPostId(value);
    }
}


