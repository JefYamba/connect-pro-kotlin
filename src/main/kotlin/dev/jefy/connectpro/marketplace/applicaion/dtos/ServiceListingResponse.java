package dev.jefy.connectpro.marketplace.applicaion.dtos;


import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse;
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.portfolio.domain.model.PService;
import dev.jefy.connectpro.portfolio.domain.vo.Tag;
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.shared.application.dtos.PricingData;

/**
 * @author Jôph Yamba
 */
public record ServiceListingResponse(
        UUID id,
        PortfolioSummaryData portfolio,
        String title,
        String description,
        CategoryResponse category,
        List<String> tags,
        String coverImageUrl,
        PricingData pricing,
        AwardResponse award,
        ServiceReviewData reviewData
) {
    
    public static ServiceListingResponse from(
            PService service, 
            PortfolioSummaryData portfolio, 
            CategoryResponse category, 
            AwardResponse award,
            ServiceReviewData reviewData
    ) {
        return new ServiceListingResponse(
                service.getId().value(),
                portfolio,
                service.getTitle(),
                service.getDescription(),
                category,
                service.getTags().stream().map(Tag::value).toList(),
                service.getCoverImageUrl().value(),
                PricingData.from(service.getPricing()),
                award,
                reviewData
        );
    }
}
