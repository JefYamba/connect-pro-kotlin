package dev.jefy.connectpro.engagement.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.engagement.domain.Review;
import dev.jefy.connectpro.engagement.domain.vo.ReviewId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface ReviewRepository extends AggregateRepository<Review, ReviewId> {

    List<Review> findByIdServiceId(UUID serviceId);
    
    long countByIdServiceId(UUID serviceId);

    @Query("""
        SELECT AVG(review.rating.value)
        FROM Review review
        WHERE review.id.serviceId = :serviceId
    """)
    Double averageRating(UUID serviceId);

    List<Review> findTop10ByIdServiceIdOrderByCreatedAtDesc(UUID serviceId);
}
