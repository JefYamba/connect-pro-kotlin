package dev.jefy.connectpro.marketplace.applicaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationRequest;
import dev.jefy.connectpro.marketplace.applicaion.exception.JobPostNotExistOrValidException;
import dev.jefy.connectpro.marketplace.domain.model.JobApplication;
import dev.jefy.connectpro.marketplace.domain.repository.JobApplicationRepository;
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId;
import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.user.UserClient;
import dev.jefy.connectpro.user.applicaion.dtos.UserData;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
@RequiredArgsConstructor
public class JobApplicationCommandImpl implements JobApplicationCommand {
    private final JobApplicationRepository jobApplicationRepo;
    private final PortfolioClient portfolioClient;
    private final UserClient userClient;
    
    @Override
    public JobApplicationId apply(JobApplicationRequest request) {
        Assert.notNull(request, "JobApplicationRequest must not be null");
        JobPostId jobPostId = JobPostId.of(request.jobPostId());
        if (!portfolioClient.existsAndValidJobPost(jobPostId)) {
            throw new JobPostNotExistOrValidException();
        }
        UserData user = userClient.getCurrentUser();
        JobApplication application = new JobApplication(user.id(), jobPostId, request.motivation());
        jobApplicationRepo.save(application);
        
        return application.getId();
    }

    @Override
    public JobApplicationId accept(JobApplicationId applicationId) {
        JobApplication application = getJobApplication(applicationId);
        application.accept();
        jobApplicationRepo.save(application);
        return application.getId();
    }
    @Override
    public JobApplicationId reject(JobApplicationId applicationId) {
        JobApplication application = getJobApplication(applicationId);
        application.reject();
        jobApplicationRepo.save(application);
        return application.getId();
    }
    
    private JobApplication getJobApplication(JobApplicationId applicationId) {
        Assert.notNull(applicationId, "JobApplicationId must not be null");
        return  jobApplicationRepo.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFound("Job Application with id " + applicationId + " not found"));
    }
}
