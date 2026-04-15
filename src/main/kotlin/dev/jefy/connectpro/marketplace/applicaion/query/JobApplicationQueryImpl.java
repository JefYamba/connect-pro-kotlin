package dev.jefy.connectpro.marketplace.applicaion.query;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationResponseForEmployer;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationResponseForUser;
import dev.jefy.connectpro.marketplace.domain.model.JobApplication;
import dev.jefy.connectpro.marketplace.domain.repository.JobApplicationRepository;
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId;
import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.user.UserClient;
import dev.jefy.connectpro.user.application.dtos.UserData;
import dev.jefy.connectpro.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobApplicationQueryImpl implements JobApplicationQuery {
    private final JobApplicationRepository applicationRepo;
    private final UserClient userClient;
    private final PortfolioClient portfolioClient;

    @Override
    public JobApplicationResponseForUser getForUser(JobPostId jobPostId) {
        Assert.notNull(jobPostId, "Job post id cannot be null");
        UserData user = userClient.getCurrentUser();
        JobApplicationId id = JobApplicationId.of(user.id(), jobPostId);
        
        return applicationRepo
                .findById(id)
                .map(mapForUser())
                .orElseThrow(()-> new ResourceNotFound("job application not found"));
    }
    
    @Override
    public JobApplicationResponseForEmployer getForEmployer(JobApplicationId applicationId) {
        Assert.notNull(applicationId, "Job application id cannot be null");
        
        return applicationRepo
                .findById(applicationId)
                .map(mapFoEmployer())
                .orElseThrow(()-> new ResourceNotFound("job application not found"));
    }

    @Override
    public List<JobApplicationResponseForUser> findForCurrentUser() {
        UserData user = userClient.getCurrentUser();
        return applicationRepo
                .findAllByApplicantId(user.id().value())
                .stream()
                .map(mapForUser())
                .toList();
    }

    @Override
    public List<JobApplicationResponseForEmployer> findByJobPost(JobPostId jobPostId) {
        return  applicationRepo
                .findAllByJobPostId(jobPostId.value())
                .stream()
                .map(mapFoEmployer())
                .toList();
    }

    private Function<JobApplication, JobApplicationResponseForUser> mapForUser() {
        return application -> {
            var jobPostId = JobPostId.of(application.getId().jobPostId());
            var jobPost = portfolioClient.getJobPostListing(jobPostId);
            return JobApplicationResponseForUser.fromDomain(application, jobPost);
        };
    }

    private Function<JobApplication, JobApplicationResponseForEmployer> mapFoEmployer() {
        return application -> {
            var applicantId = UserId.of(application.getId().applicantId());
            var user =  userClient.getData(applicantId);
            return JobApplicationResponseForEmployer.fromDomain(application, user);
        };
    }

}
