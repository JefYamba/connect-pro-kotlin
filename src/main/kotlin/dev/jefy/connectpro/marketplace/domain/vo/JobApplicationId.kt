package dev.jefy.connectpro.marketplace.domain.vo;


import org.springframework.util.Assert;

import java.util.UUID;

import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.shared.infrastructure.ddd.DIdentifier;
import dev.jefy.connectpro.user.domain.vo.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */

@Embeddable
public record JobApplicationId(
        @Column(name = "applicant_id")
        UUID applicantId,
        @Column(name = "job_post_id")
        UUID jobPostId
) implements DIdentifier<JobApplicationId> {
    
    public JobApplicationId {
        Assert.notNull(applicantId, "applicantId cannot be null");
        Assert.notNull(jobPostId, "jobPostId cannot be null");
    }
    public static JobApplicationId of(UserId applicantId, JobPostId jobPostId) {
        return new JobApplicationId(applicantId.value(), jobPostId.value());
    }

    @Override
    public JobApplicationId value() {
        return this;
    }
}