package dev.jefy.connectpro.portfolio.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.portfolio.domain.model.JobPost;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface JobPostRepository extends AggregateRepository<JobPost, JobPostId> {
    @Query("""
        select count(jobPost) > 0 from JobPost jobPost
        where jobPost.portfolioId = :portfolioId
        and jobPost.title = :title
    """)
    boolean isTitleConflict(@Param("id") PortfolioId portfolioId, @Param("title") String title);

    boolean existsByCategoryId(CategoryId categoryId);

    List<JobPost> findAllByPortfolioId(PortfolioId id);
    @Query("""
        select jobPost from JobPost jobPost
        where (:categoryId is null or jobPost.categoryId = :categoryId)
          and (:search is null or (
                lower(jobPost.title) like lower(concat('%', :search, '%'))
             or lower(jobPost.description) like lower(concat('%', :search, '%'))
        ))
    """)
    Page<JobPost> filter(@Param("search") String search, @Param("categoryId") CategoryId categoryId, Pageable page);
}
