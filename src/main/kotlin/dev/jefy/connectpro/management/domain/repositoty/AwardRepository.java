package dev.jefy.connectpro.management.domain.repositoty;

import org.springframework.stereotype.Repository;

import dev.jefy.connectpro.management.domain.Award;
import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface AwardRepository extends AggregateRepository<Award, AwardId> {
    boolean existsByName(String name);
}

