package dev.jefy.connectpro.marketplace.applicaion.query;

import org.jspecify.annotations.NullMarked;

import java.util.List;

import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationResponseForEmployer;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationResponseForUser;
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface JobApplicationQuery {
    JobApplicationResponseForUser getForUser(JobPostId jobPostId);
    JobApplicationResponseForEmployer getForEmployer(JobApplicationId applicationId);
    List<JobApplicationResponseForUser> findForCurrentUser();
    List<JobApplicationResponseForEmployer> findByJobPost(JobPostId jobPostId);
}
