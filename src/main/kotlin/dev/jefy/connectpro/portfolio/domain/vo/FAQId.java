package dev.jefy.connectpro.portfolio.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */
public record FAQId (UUID value) implements DIdentifier<UUID> {
    public FAQId {
        Assert.notNull(value, "value cannot be null");
    }

    public static FAQId generate() {
        return new  FAQId(UUID.randomUUID());
    }

    public static FAQId of(UUID faqId) {
        return new  FAQId(faqId);
    }
}
