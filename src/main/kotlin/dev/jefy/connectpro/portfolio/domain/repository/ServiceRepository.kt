package dev.jefy.connectpro.portfolio.domain.repository

import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.domain.model.Service
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ServiceRepository : JpaRepository<Service, ServiceId> {

    @Query(
        """
        select case when count(service) > 0 then true else false end
        from Service service
        where service.portfolioId = :portfolioId
        and service.name = :title
    """
    )
    fun existsByTitleConflict(
        @Param("portfolioId") portfolioId: PortfolioId,
        @Param("title") title: String,
    ): Boolean
    
    @Query(
        """
        select case when count(service) > 0 then true else false end
        from Service service
        where service.portfolioId = :portfolioId
        and service.name = :title
        and service.id <> :serviceId
    """
    )
    fun existsByTitleConflictForId(
        @Param("portfolioId") portfolioId: PortfolioId,
        @Param("title") title: String,
        @Param("serviceId") serviceId: ServiceId
    ): Boolean

    fun existsByCategoryId(categoryId: CategoryId): Boolean

    fun existsByAwardId(awardId: AwardId): Boolean

    fun findAllByPortfolioId(portfolioId: PortfolioId): List<Service>

    @Query(
        """
        select service from Service service
        where (:categoryId is null or service.categoryId = :categoryId)
          and (:search is null or (
                lower(service.name) like lower(concat('%', :search, '%'))
             or lower(service.description) like lower(concat('%', :search, '%'))
        ))
        """
    )
    fun filter(
        @Param("search") search: String?,
        @Param("categoryId") categoryId: CategoryId?,
        page: Pageable
    ): Page<Service>
}