package dev.jefy.connectpro.portfolio.applicaion.command;

import org.jspecify.annotations.NullMarked;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.portfolio.applicaion.dtos.FAQRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ServiceRequest;
import dev.jefy.connectpro.portfolio.domain.vo.FAQId;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface ServiceCommand {
    
    ServiceId create(ServiceRequest request);
    ServiceId update(ServiceId serviceId, ServiceRequest request);
    ServiceId setCoverImage(ServiceId serviceId, MultipartFile image) throws IOException;
    ServiceId deleteCoverImage(ServiceId serviceId) throws IOException;
    ServiceId addImage(ServiceId serviceId, MultipartFile image) throws IOException;
    ServiceId removeImage(ServiceId serviceId, ImageUrl imageUrl) throws IOException;
    ServiceId addFaq(ServiceId serviceId, FAQRequest request);
    ServiceId removeFaq(ServiceId serviceId, FAQId faqId);
    ServiceId setAward(ServiceId serviceId, AwardId  awardId);
    ServiceId removeAward(ServiceId serviceId);
    ServiceId activateService(ServiceId serviceId);
    ServiceId deactivateService(ServiceId serviceId);
    
}
