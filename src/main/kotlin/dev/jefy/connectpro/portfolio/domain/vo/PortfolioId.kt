package dev.jefy.connectpro.portfolio.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;

/**
 * @author Jôph Yamba
 */

public record PortfolioId(UUID value) implements DIdentifier<UUID> {
    public PortfolioId {
        Assert.notNull(value, "Portfolio value cannot be null");
    }

    public static PortfolioId generate() {
        return new PortfolioId(UUID.randomUUID());
    }

    public static PortfolioId of(UUID portfolioId) {
        return new  PortfolioId(portfolioId);
    }
}



