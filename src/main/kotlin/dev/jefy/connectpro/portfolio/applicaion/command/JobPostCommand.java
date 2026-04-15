package dev.jefy.connectpro.portfolio.applicaion.command;


import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.portfolio.applicaion.dtos.JobPostRequest;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface JobPostCommand {
    
    JobPostId create(JobPostRequest request);
    JobPostId update(JobPostId jobPostId, JobPostRequest request);
    JobPostId open(JobPostId jobPostId);
    JobPostId close(JobPostId jobPostId);
}
