package dev.jefy.connectpro.management.domain.repositoty;

import org.springframework.stereotype.Repository;

import dev.jefy.connectpro.management.domain.Badge;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface BadgeRepository extends AggregateRepository<Badge, BadgeId> {
    boolean existsByName(String name);
}

