package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.management.ManagementClient
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.marketplace.MarketplaceClient
import dev.jefy.connectpro.portfolio.application.dtos.JobPostRequest
import dev.jefy.connectpro.portfolio.application.exceptions.CategoryNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.JobPostAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.JobPostNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioNotFoundException
import dev.jefy.connectpro.portfolio.domain.model.JobPost
import dev.jefy.connectpro.portfolio.domain.repository.JobPostRepository
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class JobPostCommandImpl(
    private val jobPostRepo: JobPostRepository,
    private val portfolioRepo: PortfolioRepository,
    private val managementClient: ManagementClient,
    private val marketplaceClient: MarketplaceClient
) : JobPostCommand {

    override fun create(request: JobPostRequest): JobPostId {
        val portfolioId = PortfolioId.of(request.portfolioId)

        if (managementClient.notExistsCategory(CategoryId.of(request.categoryId))) {
            throw CategoryNotFoundException()
        }

        if (!portfolioRepo.existsById(portfolioId)) throw  PortfolioNotFoundException()
        
        val isConflict = jobPostRepo.existsByTitleConflict(portfolioId = portfolioId, title = request.title)
        if (isConflict) throw JobPostAlreadyExistsException()

        val jobPost = JobPost(portfolioId, request)
        jobPostRepo.save(jobPost)

        return jobPost.id
    }

    override fun update(jobPostId: JobPostId, request: JobPostRequest): JobPostId {
        val jobPost = getJobPost(jobPostId)

        if (managementClient.notExistsCategory(CategoryId.of(request.categoryId))) {
            throw CategoryNotFoundException()
        }

        if ( jobPostRepo.existsByTitleConflictForId(
            portfolioId = jobPost.portfolioId, 
            title = request.title,
            jobPostId = jobPostId
        )) throw JobPostAlreadyExistsException()

        jobPost.update(request)
        jobPostRepo.save(jobPost)

        return jobPost.id
    }
    
    override fun open(jobPostId: JobPostId): JobPostId {
        val jobPost = getJobPost(jobPostId)

        jobPost.open()
        jobPostRepo.save(jobPost)

        return jobPost.id
    }

    override fun close(jobPostId: JobPostId): JobPostId {
        val jobPost = getJobPost(jobPostId)

        jobPost.close()
        jobPostRepo.save(jobPost)

        return jobPost.id
    }

    override fun delete(jobPostId: JobPostId) {
        val jobPost = getJobPost(jobPostId)
        if (marketplaceClient.jobPostHasApplications(jobPostId)) {
            throw IllegalStateException("Cannot delete job post with applications")
        } 
        jobPostRepo.deleteById(jobPost.id)
    }

    private fun getJobPost(id: JobPostId): JobPost = jobPostRepo
        .findById(id).orElseThrow { JobPostNotFoundException() }
    
}