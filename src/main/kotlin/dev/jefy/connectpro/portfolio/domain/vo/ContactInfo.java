package dev.jefy.connectpro.portfolio.domain.vo;

import org.springframework.util.Assert;

import dev.jefy.connectpro.user.domain.vo.Email;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record ContactInfo(
        @Embedded
        @AttributeOverride(name = "value", column = @Column(name = "email"))
        Email email,
        @Column(nullable = false)
        String phone1,
        String phone2, // Optional
        String websiteUrl // Optional
) {
    public ContactInfo {
        Assert.notNull(email, "value is required");
        Assert.hasText(phone1, "phone1 is required");
    }
}

