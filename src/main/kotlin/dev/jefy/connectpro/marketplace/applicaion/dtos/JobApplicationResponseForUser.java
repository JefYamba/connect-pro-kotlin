package dev.jefy.connectpro.marketplace.applicaion.dtos;


import java.time.Instant;

import dev.jefy.connectpro.marketplace.domain.model.JobApplication;
import dev.jefy.connectpro.shared.domain.vo.JobApplicationStatus;

/**
 * @author Jôph Yamba
 */
public record JobApplicationResponseForUser(
        JobPostListingResponse jobPost,
        String motivation,
        Instant appliedAt,
        JobApplicationStatus status
) {
    public static JobApplicationResponseForUser fromDomain(
            JobApplication application,
            JobPostListingResponse jobPostListingResponse
    ) {
        return new JobApplicationResponseForUser(
                jobPostListingResponse,
                application.getMotivation(),
                application.getAppliedAt(),
                application.getStatus()
        );
    }
}
