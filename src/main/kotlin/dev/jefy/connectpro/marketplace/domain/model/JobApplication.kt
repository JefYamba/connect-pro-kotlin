package dev.jefy.connectpro.marketplace.domain.model;

import org.springframework.util.Assert;

import java.time.Instant;

import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.shared.domain.vo.JobApplicationStatus;
import dev.jefy.connectpro.shared.infrastructure.ddd.DAggregateRoot;
import dev.jefy.connectpro.user.domain.vo.UserId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "job_applications")
public class JobApplication implements DAggregateRoot<JobApplicationId> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private JobApplicationId id;
    
    private String motivation;
    
    private Instant appliedAt;
    
    @Enumerated(EnumType.STRING)
    private JobApplicationStatus status;

    public JobApplication(UserId applicantId, JobPostId jobPostId, String motivation) {
        Assert.notNull(applicantId, "applicantId cannot be null");
        Assert.notNull(jobPostId, "jobPostId cannot be null");
        this.id = JobApplicationId.of(applicantId, jobPostId);
        this.motivation = motivation;
        this.appliedAt = Instant.now();
        this.status = JobApplicationStatus.PENDING;
    }
    
    public void accept() {
        this.status = JobApplicationStatus.ACCEPTED;
    }
    public void reject() {
        this.status = JobApplicationStatus.REJECTED;
    }
}
