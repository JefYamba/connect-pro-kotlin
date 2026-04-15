package dev.jefy.connectpro.portfolio.applicaion.command;

import org.jspecify.annotations.NullMarked;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.portfolio.applicaion.dtos.*;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType;
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface PortfolioCommand {
    PortfolioId create(PortfolioRequest request);
    PortfolioId updateGeneralInfo(PortfolioId portfolioId, GeneralInfoRequest request);
    PortfolioId updateProfessional(PortfolioId portfolioId, ProfessionalInfoRequest request);
    PortfolioId updateContactInfo(PortfolioId portfolioId, ContactInfoData request);
    PortfolioId updateLocationInfo(PortfolioId portfolioId, LocationInfoData request);
    PortfolioId setCoverImage(PortfolioId portfolioId, MultipartFile image) throws IOException;
    PortfolioId deleteCoverImage(PortfolioId portfolioId) throws IOException;
    PortfolioId addSocialLink(PortfolioId portfolioId, SocialLinkData linkData);
    PortfolioId deleteSocialLink(PortfolioId portfolioId, SocialLinkId socialLinkId);
    PortfolioId activate(PortfolioId portfolioId);
    PortfolioId deactivate(PortfolioId portfolioId);
    PortfolioId bloc(PortfolioId portfolioId);
    PortfolioId changeType(PortfolioId portfolioId, PortfolioType type);
    PortfolioId setBadge(PortfolioId portfolioId, BadgeId badgeId);
}
