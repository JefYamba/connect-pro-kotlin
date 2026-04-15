package dev.jefy.connectpro.portfolio.applicaion.dtos;

import java.util.List;

import dev.jefy.connectpro.portfolio.domain.vo.Availability;
import dev.jefy.connectpro.portfolio.domain.vo.ProfessionalInfo;

/**
 * @author Jôph Yamba
 */
public record ProfessionalInfoResponse(
    int activeYears,
    int numberOfEmployees,
    List<ProjectResponse> projects,
    List<Availability> availabilities
) {
    public static ProfessionalInfoResponse from(ProfessionalInfo professionalInfo, List<ProjectResponse> projects) {
        return new ProfessionalInfoResponse(
                professionalInfo.activeYears(),
                professionalInfo.numberOfEmployees(),
                projects,
                professionalInfo.availabilities()
        );
    }
}
