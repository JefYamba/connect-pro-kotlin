package dev.jefy.connectpro.marketplace.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.marketplace.domain.model.JobApplication;
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface JobApplicationRepository extends AggregateRepository<JobApplication, JobApplicationId> {
    
    @Query("""
        select application from JobApplication application
        where application.id.applicantId = :applicantId
    """)
    List<JobApplication> findAllByApplicantId(@Param("applicantId") UUID applicantId);
    
    
    @Query("""
        select application from JobApplication application
        where application.id.jobPostId = :jobPostId
    """)
    List<JobApplication> findAllByJobPostId(@Param("jobPostId") UUID jobPostId);
}
