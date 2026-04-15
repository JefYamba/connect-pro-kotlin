package dev.jefy.connectpro.portfolio.applicaion.dtos;


import org.springframework.util.Assert;

import java.util.List;

import dev.jefy.connectpro.portfolio.domain.vo.Availability;
import dev.jefy.connectpro.portfolio.domain.vo.ProfessionalInfo;

/**
 * @author Jôph Yamba
 */
public record ProfessionalInfoRequest(
    int activeYears,
    int numberOfEmployees,
    List<Availability> availabilities
) {

    public ProfessionalInfoRequest {
        Assert.isTrue(numberOfEmployees > 0, "numberOfEmployees must be greater than 0");
    }

    public ProfessionalInfo toDomain(){
        return new ProfessionalInfo(
                this.numberOfEmployees,
                this.activeYears,
                this.availabilities
        );
    }
}
