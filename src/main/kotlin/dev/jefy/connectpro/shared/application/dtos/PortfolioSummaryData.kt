package dev.jefy.connectpro.shared.application.dtos;


import java.util.UUID;

import dev.jefy.connectpro.portfolio.domain.model.Portfolio;

/**
 * @author Jôph Yamba
 */
public record PortfolioSummaryData(
        UUID id,
        String name,
        String coverImageUrl,
        String shortDescription,
        boolean active
) {
    public static PortfolioSummaryData from(Portfolio portfolio) {
        String imageUrl = portfolio.getGeneralInfo().coverImageUrl() == null
                ? null
                : portfolio.getGeneralInfo().coverImageUrl().value();
        return new  PortfolioSummaryData(
                portfolio.getId().value(),
                portfolio.getGeneralInfo().name(),
                imageUrl,
                portfolio.getGeneralInfo().shortDescription(),
                portfolio.isActive()
        );
    }
}
