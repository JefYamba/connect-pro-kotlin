package dev.jefy.connectpro.portfolio.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.UserId
import java.util.Optional

@Repository
interface PortfolioRepository : JpaRepository<Portfolio, PortfolioId> {

    @Query("""
        select portfolio from Portfolio portfolio where portfolio.userId = :userId
    """)
    fun findByUserId(@Param("userId") userId: UserId): Optional<Portfolio>

    @Query("""
        select count(portfolio) > 0 from Portfolio portfolio
        where portfolio.generalInfo.name = :name
        or portfolio.contactInfo.email = :email
        or portfolio.contactInfo.phone1 in :phones
        or portfolio.contactInfo.phone2 in :phones
    """)
    fun existsPortfolioConflict(
        @Param("name") name: String,
        @Param("email") email: Email,
        @Param("phones") phones: List<String>
    ): Boolean

    fun existsByUserId(userId: UserId): Boolean

    @Query("""
        select count(portfolio) > 0 from Portfolio portfolio
        where portfolio.id != :portfolioId
        and portfolio.generalInfo.name = :name
    """)
    fun existsNameConflict(
        @Param("id") portfolioId: PortfolioId,
        @Param("name") name: String
    ): Boolean

    @Query("""
        select count(portfolio) > 0 from Portfolio portfolio
        where portfolio.id != :portfolioId
        or portfolio.contactInfo.email = :email
        or portfolio.contactInfo.phone1 in :phones
        or portfolio.contactInfo.phone2 in :phones
    """)
    fun existsContactConflict(
        @Param("id") portfolioId: PortfolioId,
        @Param("email") email: String,
        @Param("phones") phones: List<String>
    ): Boolean

    fun existsByBadgeId(badgeId: BadgeId): Boolean
}