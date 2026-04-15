package dev.jefy.connectpro.portfolio.applicaion.dtos;


import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType;

/**
 * @author Jôph Yamba
 */
public record PortfolioRequest (
    UUID userId,
    PortfolioType type,
    GeneralInfoRequest generalInfo,
    ProfessionalInfoRequest professionalInfo,
    ContactInfoData contactInfo,
    LocationInfoData locationInfo,
    List<SocialLinkData> socialLinks  
) {}
