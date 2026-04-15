package dev.jefy.connectpro.portfolio.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record SocialLinkId (UUID value) implements DIdentifier<UUID> {
    public SocialLinkId {
        Assert.notNull(value, "value cannot be null");
    }

    public static SocialLinkId generate() {
        return new  SocialLinkId(UUID.randomUUID());
    }

    public static SocialLinkId of(String socialLinkId) {
        return new  SocialLinkId(UUID.fromString(socialLinkId));
    }
}
