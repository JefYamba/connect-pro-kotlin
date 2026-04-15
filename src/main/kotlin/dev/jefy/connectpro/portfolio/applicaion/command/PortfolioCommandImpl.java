package dev.jefy.connectpro.portfolio.applicaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.management.ManagementClient;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.portfolio.applicaion.dtos.*;
import dev.jefy.connectpro.portfolio.applicaion.service.PortfolioAppService;
import dev.jefy.connectpro.portfolio.domain.model.Portfolio;
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType;
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService;
import dev.jefy.connectpro.shared.infrastructure.messaging.EmailService;
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.PortfolioCreadtedEmailStrategy;
import dev.jefy.connectpro.user.UserClient;
import dev.jefy.connectpro.user.applicaion.dtos.UserData;
import dev.jefy.connectpro.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioCommandImpl implements PortfolioCommand {
    private final PortfolioRepository portfolioRepo;
    private final PortfolioAppService  portfolioAppService;
    private final UserClient userClient;
    private final EmailService emailService;
    private final ImageService imageService;
    private final ManagementClient managementClient;
    
    @Override
    public PortfolioId create(PortfolioRequest request) {
        var userId  = new UserId(request.userId());
        
        UserData userData = userClient.getData(userId);
        
        if (portfolioRepo.existsByUserId(userId)) 
            throw new IllegalStateException("Portfolio already exists");
        
        portfolioAppService.checkConflict(request);
        portfolioAppService.checkSocialLinksConflict(request.socialLinks());

        Portfolio portfolio = new Portfolio(
            userId,
            request.type(),
            request.generalInfo().toDomain(),
            request.professionalInfo().toDomain(),
            request.contactInfo().toDomain(),
            request.locationInfo().toDomain(),
            request.socialLinks()
        );
        portfolioRepo.save(portfolio);
        emailService.sendEmail(userData.email(), new PortfolioCreadtedEmailStrategy(userData.name()));
        return portfolio.getId();
    }

    @Override
    public PortfolioId updateGeneralInfo(PortfolioId portfolioId, GeneralInfoRequest request) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolioAppService.checkGeneralInfoConflict(portfolioId, request);
        portfolio.updateGeneralInfo(request.toDomain());
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId updateProfessional(PortfolioId portfolioId, ProfessionalInfoRequest request) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.updateProfessionalInfo(request.toDomain());
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId updateContactInfo(PortfolioId portfolioId, ContactInfoData request) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolioAppService.checkContactInfoConflict(portfolioId, request);
        portfolio.updateContactInfo(request.toDomain());
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId updateLocationInfo(PortfolioId portfolioId, LocationInfoData request) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.updateLocationInfo(request.toDomain());
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

  

    @Override
    public PortfolioId setCoverImage(PortfolioId portfolioId, MultipartFile image) throws IOException {        
        Portfolio portfolio = getPortfolio(portfolioId);
        ImageUrl imageUrl = imageService.save(image);
        portfolio.setCoverImageUrl(imageUrl);
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId deleteCoverImage(PortfolioId portfolioId) throws IOException {        
        Portfolio portfolio = getPortfolio(portfolioId);
        imageService.delete(portfolio.getGeneralInfo().coverImageUrl());
        portfolio.deleteCoverImageUrl();
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId addSocialLink(PortfolioId portfolioId, SocialLinkData linkData) {        
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.addSocialLink(linkData);
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId deleteSocialLink(PortfolioId portfolioId, SocialLinkId socialLinkId) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.deleteSocialLink(socialLinkId);
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId activate(PortfolioId portfolioId) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.activate();
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId deactivate(PortfolioId portfolioId) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.deactivate();
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId bloc(PortfolioId portfolioId) {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.block();
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId changeType(PortfolioId portfolioId, PortfolioType type) {
        Assert.notNull(type, "Portfolio Type must not be null");
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.changeType(type);
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }

    @Override
    public PortfolioId setBadge(PortfolioId portfolioId, BadgeId badgeId) {
        Assert.notNull(badgeId, "Portfolio Type must not be null");
        
        if (managementClient.notExistsBadge(badgeId)) {
            throw new ResourceNotFound("Badge with id " + badgeId + " not found");
        }
        
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.setBadgeId(badgeId);
        portfolioRepo.save(portfolio);
        return portfolio.getId();
    }


    private Portfolio getPortfolio(PortfolioId portfolioId) {
        Assert.notNull(portfolioId, "Portfolio ID must not be null");
        return portfolioRepo.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFound("Portfolio not found"));
    }

}
