package dev.jefy.connectpro.portfolio.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.domain.model.JobPost
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface JobPostRepository : JpaRepository<JobPost, JobPostId> {

    @Query("""
        select count(jobPost) > 0 from JobPost jobPost
        where jobPost.portfolioId = :portfolioId
        and jobPost.title = :title
    """)
    fun isTitleConflict(@Param("portfolioId") portfolioId: PortfolioId, @Param("title") title: String): Boolean

    fun existsByCategoryId(categoryId: CategoryId): Boolean

    fun findAllByPortfolioId(id: PortfolioId): List<JobPost>

    @Query("""
        select jobPost from JobPost jobPost
        where (:categoryId is null or jobPost.categoryId = :categoryId)
          and (:search is null or (
                lower(jobPost.title) like lower(concat('%', :search, '%'))
             or lower(jobPost.description) like lower(concat('%', :search, '%'))
        ))
    """)
    fun filter(
        @Param("search") search: String?,
        @Param("categoryId") categoryId: CategoryId?,
        page: Pageable
    ): Page<JobPost>
}