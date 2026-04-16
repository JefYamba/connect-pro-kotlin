package dev.jefy.connectpro.portfolio.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.portfolio.domain.model.PService;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface ServiceRepository extends AggregateRepository<PService, ServiceId> {
    @Query("""
        select count(service) > 0 from PService service
        where service.portfolioId = :portfolioId
        and service.title = :title
    """)
    boolean isTitleConflict(@Param("id") PortfolioId portfolioId, @Param("title") String title);

    boolean existsByCategoryId(CategoryId categoryId);

    boolean existsByAwardId(AwardId awardId);

    List<PService> findAllByPortfolioId(PortfolioId portfolioId);
    
    @Query("""
        select service from PService service
        where (:categoryId is null or service.categoryId = :categoryId)
          and (:search is null or (
                lower(service.title) like lower(concat('%', :search, '%'))
             or lower(service.description) like lower(concat('%', :search, '%'))
        ))
    """)
    Page<PService> filter(@Param("search") String search, @Param("categoryId") CategoryId categoryId, Pageable page);
}

