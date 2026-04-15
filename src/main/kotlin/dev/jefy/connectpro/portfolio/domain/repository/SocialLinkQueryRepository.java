package dev.jefy.connectpro.portfolio.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import dev.jefy.connectpro.portfolio.domain.model.SocialLink;
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId;
import dev.jefy.connectpro.shared.infrastructure.ddd.QueryRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface SocialLinkQueryRepository extends QueryRepository<SocialLink, SocialLinkId> {
    @Query("""
       select count(sl) > 0 from SocialLink sl where sl.url in :urls
    """)
    boolean existsByUrls(@Param("urls") List<String> urls);
}
