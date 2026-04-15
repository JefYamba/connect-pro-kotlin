package dev.jefy.connectpro.portfolio.applicaion.dtos;


import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewResponse;
import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse;
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.marketplace.applicaion.dtos.ServiceReviewData;
import dev.jefy.connectpro.portfolio.domain.model.PService;
import dev.jefy.connectpro.portfolio.domain.vo.Tag;
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.shared.application.dtos.PricingData;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;

/**
 * @author Jôph Yamba
 */
public record ServiceResponse(
    UUID id, 
    PortfolioSummaryData portfolio, 
    String title, 
    String description,
    CategoryResponse category, 
    List<String> tags, 
    String coverImageUrl,
    List<String> imageUrls, 
    PricingData pricing, 
    List<FAQResponse> faqs,
    AwardResponse award, 
    ServiceReviewData reviewData,
    List<ReviewResponse> recentReviews
) {
    public static ServiceResponse from(
            PService service, 
            PortfolioSummaryData portfolio, 
            CategoryResponse category, 
            AwardResponse award, 
            ServiceReviewData reviewData, 
            List<ReviewResponse> recentReviews
    ) {
        return new ServiceResponse(
                service.getId().value(),
                portfolio,
                service.getTitle(),
                service.getDescription(),
                category,
                service.getTags()
                        .stream()
                        .map(Tag::value)
                        .toList(),
                service.getCoverImageUrl().value(),
                service.getImageUrls()
                        .stream()
                        .map(ImageUrl::value)
                        .toList(),
                PricingData.from(service.getPricing()),
                service.getFaqs()
                        .stream()
                        .map(FAQResponse::from)
                        .toList(),
                award,
                reviewData,
                recentReviews
        );
    }
}

