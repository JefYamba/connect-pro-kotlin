package dev.jefy.connectpro.portfolio.applicaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import dev.jefy.connectpro.management.ManagementClient;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.portfolio.applicaion.dtos.JobPostRequest;
import dev.jefy.connectpro.portfolio.domain.model.JobPost;
import dev.jefy.connectpro.portfolio.domain.repository.JobPostRepository;
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Transactional
@Service
@RequiredArgsConstructor
public class JobPostCommandImpl implements JobPostCommand {
    private final JobPostRepository jobPostRepo;
    private final PortfolioRepository portfolioRepo;
    private final ManagementClient managementClient;
    @Override
    public JobPostId create(JobPostRequest request) {
        PortfolioId portfolioId = PortfolioId.of(request.portfolioId());
        
        if (managementClient.notExistsCategory(CategoryId.of(request.categoryId()))) {
            throw  new ResourceNotFound("Category " + request.categoryId() + " not found");
        }
        
        if (!portfolioRepo.existsById(portfolioId)) {
            throw  new ResourceNotFound("Job post with id " + portfolioId + " not found");
        }
        boolean isConflict = jobPostRepo.isTitleConflict(portfolioId, request.title());
        if (isConflict) {
            throw new IllegalStateException("Job post with title already exists");
        }
        JobPost jobPost = new JobPost(portfolioId, request);
        jobPostRepo.save(jobPost);
        return jobPost.getId();
    }

    @Override
    public JobPostId update(JobPostId jobPostId, JobPostRequest request) {
        JobPost jobPost = getJobPost(jobPostId);

        if (managementClient.notExistsCategory(CategoryId.of(request.categoryId()))) {
            throw  new ResourceNotFound("Category " + request.categoryId() + " not found");
        }
        
        boolean isConflict = jobPostRepo.isTitleConflict(jobPost.getPortfolioId(), request.title());
        if (isConflict) {
            throw new IllegalStateException("Job post with title already exists");
        }
        jobPost.update(request);
        jobPostRepo.save(jobPost);
        return jobPost.getId();
    }
    @Override
    public JobPostId open(JobPostId jobPostId) {
        JobPost jobPost = getJobPost(jobPostId);
        jobPost.open();
        jobPostRepo.save(jobPost);
        return jobPost.getId();
    }
    @Override
    public JobPostId close(JobPostId jobPostId) {
        JobPost jobPost = getJobPost(jobPostId);
        jobPost.close();
        jobPostRepo.save(jobPost);
        return jobPost.getId();
    }
    
    private JobPost getJobPost(JobPostId id) {
        Assert.notNull(id, "jobPostId must not be null");
        return  jobPostRepo
                .findById(id)
                .orElseThrow(()-> new ResourceNotFound("Job post with id " + id + " not found"));
    }

}
