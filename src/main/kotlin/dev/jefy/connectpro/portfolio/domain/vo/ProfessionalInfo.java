package dev.jefy.connectpro.portfolio.domain.vo;

import org.springframework.util.Assert;

import java.util.List;

import dev.jefy.connectpro.shared.infrastructure.converter.AvailabilityListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record ProfessionalInfo (
    Integer numberOfEmployees, // Optional
    int activeYears,
    @Convert(converter = AvailabilityListConverter.class)
    @Column(name = "availabilities", columnDefinition = "text")
    List<Availability> availabilities
){
    public ProfessionalInfo {
        if (numberOfEmployees != null) {
            Assert.isTrue(numberOfEmployees > 0, "Number of employees must be greater than 0");
        }
        Assert.notNull(availabilities, "Availabilities must not be null");
    }
}
