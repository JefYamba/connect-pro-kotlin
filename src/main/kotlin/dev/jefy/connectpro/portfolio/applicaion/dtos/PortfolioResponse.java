package dev.jefy.connectpro.portfolio.applicaion.dtos;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobPostListingResponse;
import dev.jefy.connectpro.marketplace.applicaion.dtos.ServiceListingResponse;
import dev.jefy.connectpro.portfolio.domain.model.Portfolio;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioStatus;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType;

/**
 * @author Jôph Yamba
 */
public record PortfolioResponse (
        UUID id,
        UUID userId,
        PortfolioType type,
        PortfolioStatus status,
        BadgeResponse badge,
        Instant createdAt,
        GeneralInfoResponse generalInfo,
        ProfessionalInfoResponse professionalInfo,
        ContactInfoData contactInfo,
        LocationInfoData locationInfo,
        List<SocialLinkData> socialLinks,
        List<ServiceListingResponse> services,
        List<JobPostListingResponse> jobPosts
) {
    public static PortfolioResponse from(
            Portfolio portfolio, 
            BadgeResponse badge,
            List<ServiceListingResponse> services, 
            List<JobPostListingResponse> jobPosts, 
            List<ProjectResponse> projects 
    ) {
        return new PortfolioResponse(
                portfolio.getId().value(),
                portfolio.getUserId().value(),
                portfolio.getType(),
                portfolio.getStatus(),
                badge,
                portfolio.getCreatedAt(),
                GeneralInfoResponse.from(portfolio.getGeneralInfo()),
                ProfessionalInfoResponse.from(portfolio.getProfessionalInfo(), projects),
                ContactInfoData.from(portfolio.getContactInfo()),
                LocationInfoData.from(portfolio.getLocationInfo()),
                portfolio.getSocialLinks().stream().map(SocialLinkData::from).toList(),
                services,
                jobPosts
        );
    }
}
