package dev.jefy.connectpro.portfolio.domain.repository

import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PortfolioRepository : JpaRepository<Portfolio, PortfolioId> {

    @Query("""
        select portfolio from Portfolio portfolio where portfolio.userId = :userId
    """)
    fun findByUserId(@Param("userId") userId: UserId): Optional<Portfolio>

    @Query("""
        select count(portfolio) > 0
        from Portfolio portfolio
        where portfolio.name = :name
        or (:email is not null and portfolio.contact.email = :email)
    """)
    fun existsPortfolioConflict(
        @Param("name") name: String,
        @Param("email") email: Email?,
    ): Boolean

    fun existsByUserId(userId: UserId): Boolean

    @Query("""
        select count(portfolio) > 0 from Portfolio portfolio
        where portfolio.id != :portfolioId
        and portfolio.name = :name
    """)
    fun existsNameConflict(
        @Param("portfolioId") portfolioId: PortfolioId,
        @Param("name") name: String
    ): Boolean

    @Query(
        """
        select count(portfolio) > 0 from Portfolio portfolio
        where portfolio.id != :portfolioId
        or (:email is not null and portfolio.contact.email = :email)
    """
    )
    fun existsContactConflict(
        @Param("id") portfolioId: PortfolioId,
        @Param("email") email: String?,
    ): Boolean

    fun existsByBadgeId(badgeId: BadgeId): Boolean
}