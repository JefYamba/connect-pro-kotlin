package dev.jefy.connectpro.portfolio.applicaion.dtos;


import dev.jefy.connectpro.portfolio.domain.vo.ContactInfo;
import dev.jefy.connectpro.user.domain.vo.Email;

/**
 * @author Jôph Yamba
 */
public record ContactInfoData(
        String email,
        String phone1,
        String phone2,
        String websiteUrl
) {
    public static ContactInfoData from(ContactInfo contactInfo) {
        return new ContactInfoData(
                contactInfo.email().value(),
                contactInfo.phone1(),
                contactInfo.phone2(),
                contactInfo.websiteUrl()
        );
    }

    public ContactInfo toDomain(){
        return new ContactInfo(
                new Email(this.email),
                this.phone1,
                this.phone2,
                this.websiteUrl
        );
        
    }
}


