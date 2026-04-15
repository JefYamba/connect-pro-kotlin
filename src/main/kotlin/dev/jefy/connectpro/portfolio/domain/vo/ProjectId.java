package dev.jefy.connectpro.portfolio.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record ProjectId(UUID value)  implements DIdentifier<UUID> {
    public ProjectId {
        Assert.notNull(value, "value cannot be null");
    }

    public static ProjectId generate() {
        return new ProjectId(UUID.randomUUID());
    }

    public static ProjectId of(UUID projectId) {
        return new ProjectId(projectId);
    }
}
