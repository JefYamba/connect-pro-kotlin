package dev.jefy.connectpro.marketplace.applicaion.command;

import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationRequest;
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId;

/**
 * @author Jôph Yamba
 */

@NullMarked
public interface JobApplicationCommand {
    
    JobApplicationId apply(JobApplicationRequest request);
    JobApplicationId accept(JobApplicationId applicationId);
    JobApplicationId reject(JobApplicationId applicationId);
}
