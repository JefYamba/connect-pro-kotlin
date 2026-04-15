package dev.jefy.connectpro.portfolio.applicaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.management.ManagementClient;
import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.portfolio.applicaion.dtos.FAQRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ServiceRequest;
import dev.jefy.connectpro.portfolio.domain.model.PService;
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository;
import dev.jefy.connectpro.portfolio.domain.repository.ServiceRepository;
import dev.jefy.connectpro.portfolio.domain.vo.FAQId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@RequiredArgsConstructor
@Transactional
public class ServiceCommandImpl implements ServiceCommand {
    
    private final PortfolioRepository  portfolioRepo;
    private final ServiceRepository serviceRepo;
    private final ImageService imageService;
    private final ManagementClient managementClient;
    
    @Override
    public ServiceId create(ServiceRequest request) {
        PortfolioId portfolioId = PortfolioId.of(request.portfolioId());


        if (managementClient.notExistsCategory(CategoryId.of(request.categoryId()))) {
            throw  new ResourceNotFound("Category " + request.categoryId() + " not found");
        }
        
        if (!portfolioRepo.existsById(portfolioId)) {
            throw  new ResourceNotFound("Portfolio with id " + portfolioId + " not found");
        }
        boolean isConflict = serviceRepo.isTitleConflict(portfolioId, request.title());
        if (isConflict) {
            throw new IllegalStateException("Service title already exists");
        }
        PService service = new PService(portfolioId, request);
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId update(ServiceId serviceId, ServiceRequest request) {
        PService service = getService(serviceId);
        
        if (managementClient.notExistsCategory(CategoryId.of(request.categoryId()))) {
            throw  new ResourceNotFound("Category " + request.categoryId() + " not found");
        }
        
        boolean isConflict = serviceRepo.isTitleConflict(service.getPortfolioId(), request.title());
        if (isConflict) {
            throw new IllegalStateException("Service title already exists");
        }
        service.update(request);
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId setCoverImage(ServiceId serviceId, MultipartFile image) throws IOException {
        Assert.notNull(image, "Image must not be null");
        PService service = getService(serviceId);

        ImageUrl imageUrl = imageService.save(image);
        service.setCoverImageUrl(imageUrl);
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId deleteCoverImage(ServiceId serviceId) throws IOException {
        PService service = getService(serviceId);
        imageService.delete(service.getCoverImageUrl());
        service.deleteCoverImageUrl();
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId addImage(ServiceId serviceId, MultipartFile image) throws IOException {
        Assert.notNull(image, "Image must not be null");
        PService service = getService(serviceId);

        ImageUrl imageUrl = imageService.save(image);
        service.addImage(imageUrl);
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId removeImage(ServiceId serviceId, ImageUrl imageUrl) throws IOException {
        Assert.notNull(imageUrl, "Image must not be null");
        PService service = getService(serviceId);
        imageService.delete(imageUrl);
        service.removeImage(imageUrl);
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId addFaq(ServiceId serviceId, FAQRequest request) {
        Assert.notNull(request, "Faq must not be null");
        PService service = getService(serviceId);
        service.addFaq(request);
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId removeFaq(ServiceId serviceId, FAQId faqId) {
        Assert.notNull(faqId, "Faq Id must not be null");
        PService service = getService(serviceId);
        service.removeFaq(faqId);
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId setAward(ServiceId serviceId, AwardId awardId) {
        Assert.notNull(awardId, "award Id must not be null");


        if (managementClient.notExistsAward(awardId)) {
            throw  new ResourceNotFound("Award " + awardId.value() + " not found");
        }
        
        PService service = getService(serviceId);
        service.setAwardId(awardId);
        serviceRepo.save(service);
        return service.getId();
    }    
    
    @Override
    public ServiceId removeAward(ServiceId serviceId) {
        PService service = getService(serviceId);
        service.removeAward();
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId activateService(ServiceId serviceId) {
        PService service = getService(serviceId);
        service.activate();
        serviceRepo.save(service);
        return service.getId();
    }

    @Override
    public ServiceId deactivateService(ServiceId serviceId) {
        PService service = getService(serviceId);
        service.deactivate();
        serviceRepo.save(service);
        return service.getId();
    }
    
    private PService getService(ServiceId serviceId) {
        Assert.notNull(serviceId, "Service id cannot be null");
        return serviceRepo
                .findById(serviceId)
                .orElseThrow(() -> new ResourceNotFound("Service not found"));
    }
}
