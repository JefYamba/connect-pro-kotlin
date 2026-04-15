package dev.jefy.connectpro.marketplace.applicaion.dtos;


import java.time.Instant;

import dev.jefy.connectpro.marketplace.domain.model.JobApplication;
import dev.jefy.connectpro.shared.domain.vo.JobApplicationStatus;
import dev.jefy.connectpro.user.applicaion.dtos.UserData;

/**
 * @author Jôph Yamba
 */
public record JobApplicationResponseForEmployer(
        UserData applicant,
        String motivation,
        Instant appliedAt,
        JobApplicationStatus status
) {
    public static JobApplicationResponseForEmployer fromDomain(
            JobApplication application,
            UserData applicant
    ) {
        return new JobApplicationResponseForEmployer(
                applicant,
                application.getMotivation(),
                application.getAppliedAt(),
                application.getStatus()
        );
    }
}
