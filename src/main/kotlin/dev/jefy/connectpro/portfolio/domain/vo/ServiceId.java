package dev.jefy.connectpro.portfolio.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record ServiceId(UUID value)  implements DIdentifier<UUID> {
    public ServiceId {
        Assert.notNull(value, "value cannot be null");
    }

    public static ServiceId generate() {
        return new ServiceId(UUID.randomUUID());
    }

    public static ServiceId of(UUID serviceId) {
        return new  ServiceId(serviceId);
    }
}


