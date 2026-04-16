package dev.jefy.connectpro.portfolio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.portfolio.domain.model.Portfolio;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.user.domain.vo.Email;
import dev.jefy.connectpro.user.domain.vo.UserId;

/**
 * @author Jôph Yamba
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, PortfolioId> {
    @Query("""
        select portfolio from Portfolio portfolio where portfolio.userId = :userId
    """)
    Optional<Portfolio> findByUserId(@Param("userId") UserId userId);

    @Query("""
        select count(portfolio) > 0 from Portfolio portfolio
        where portfolio.generalInfo.name = :name
        or portfolio.contactInfo.email = :email
        or portfolio.contactInfo.phone1 in :phones
        or portfolio.contactInfo.phone2 in :phones
    """)
    boolean existsPortfolioConflict(
           @Param("name") String name,
           @Param("email") Email email,
           @Param("phones") List<String> phones
    );

    boolean existsByUserId(UserId userId);

    @Query("""
        select count(portfolio) > 0 from Portfolio portfolio
        where portfolio.id != :portfolioId
        and portfolio.generalInfo.name = :name
    """)
    boolean existsNameConflict(
            @Param("id") PortfolioId portfolioId,
            @Param("name") String name
    );

    @Query("""
        select count(portfolio) > 0 from Portfolio portfolio
        where portfolio.id != :portfolioId
        or portfolio.contactInfo.email = :email
        or portfolio.contactInfo.phone1 in :phones
        or portfolio.contactInfo.phone2 in :phones
    """)
    boolean existsContactConflict(
        @Param("id") PortfolioId portfolioId,
        @Param("email") String email,
        @Param("phones") List<String> phones
    );

    boolean existsByBadgeId(BadgeId badgeId);
}


