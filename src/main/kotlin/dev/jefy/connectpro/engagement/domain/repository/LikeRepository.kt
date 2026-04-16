package dev.jefy.connectpro.engagement.domain.repository;


import org.springframework.stereotype.Repository;

import java.util.UUID;

import dev.jefy.connectpro.engagement.domain.Like;
import dev.jefy.connectpro.engagement.domain.vo.LikeId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface LikeRepository extends AggregateRepository<Like, LikeId> {

    boolean existsById(LikeId id);
    
    long countByIdServiceId(UUID serviceId);

    void deleteById(LikeId id);
}

