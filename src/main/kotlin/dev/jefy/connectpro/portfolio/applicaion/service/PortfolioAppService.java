package dev.jefy.connectpro.portfolio.applicaion.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import dev.jefy.connectpro.portfolio.applicaion.dtos.ContactInfoData;
import dev.jefy.connectpro.portfolio.applicaion.dtos.GeneralInfoRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.PortfolioRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.SocialLinkData;
import dev.jefy.connectpro.portfolio.applicaion.exceptions.PortfolioAlreadyExistsException;
import dev.jefy.connectpro.portfolio.applicaion.exceptions.SocialLinkAlreadyExistsException;
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository;
import dev.jefy.connectpro.portfolio.domain.repository.SocialLinkQueryRepository;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.user.domain.vo.Email;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@RequiredArgsConstructor
public class PortfolioAppService {
    
    private final PortfolioRepository portfolioRepos;
    private final SocialLinkQueryRepository socialLinkRepo;
    
    public void checkConflict(PortfolioRequest request){
        List<String> phones = Stream
                .of(request.contactInfo().phone1(), request.contactInfo().phone2())
                .filter(Objects::nonNull)
                .toList();
        
        if (phones.isEmpty()) {
            throw new IllegalArgumentException("At least one phone number is required");
        }
        
        boolean isConflict = portfolioRepos
                .existsPortfolioConflict(request.generalInfo().name(), Email.of(request.contactInfo().email()), phones);
        
        if (isConflict) throw new PortfolioAlreadyExistsException("Portfolio conflict");
    }
    
    public void checkSocialLinksConflict(List<SocialLinkData> socialLinks) {
        List<String> urls = socialLinks.stream()
                .map(SocialLinkData::url)
                .filter(Objects::nonNull)
                .toList();

        if (urls.isEmpty()) return;

        boolean exists = socialLinkRepo.existsByUrls(urls);

        if (exists) {
            throw new SocialLinkAlreadyExistsException("One or more social links already exist");
        }
    }

    public void checkGeneralInfoConflict(PortfolioId portfolioId, GeneralInfoRequest request) {
        boolean isConflict = portfolioRepos
                .existsNameConflict(portfolioId,request.name());
        if (isConflict) throw new PortfolioAlreadyExistsException("Different portfolio with the same name already exists");
    }

    public void checkContactInfoConflict(PortfolioId portfolioId, ContactInfoData request) {
        List<String> phones = Stream
                .of(request.phone1(), request.phone2())
                .filter(Objects::nonNull)
                .toList();

        if (phones.isEmpty()) {
            throw new IllegalArgumentException("At least one phone number is required");
        }

        boolean isConflict = portfolioRepos
                .existsContactConflict(portfolioId, request.email(), phones);

        if (isConflict) throw new PortfolioAlreadyExistsException("Different portfolio with the same email or phone already exists");
    }
}
