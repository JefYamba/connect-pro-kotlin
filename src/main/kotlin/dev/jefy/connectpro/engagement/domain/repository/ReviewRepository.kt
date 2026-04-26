package dev.jefy.connectpro.engagement.domain.repository

import dev.jefy.connectpro.engagement.domain.Review
import dev.jefy.connectpro.engagement.domain.vo.ReviewId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewRepository : JpaRepository<Review, ReviewId> {

    fun findByIdServiceId(serviceId: UUID): List<Review>
    
    fun countByIdServiceId(serviceId: UUID): Long

    @Query("""
        SELECT AVG(review.rating.value)
        FROM Review review
        WHERE review.id.serviceId = :serviceId
    """)
    fun averageRating(serviceId: UUID): Double?

    fun findTop10ByIdServiceIdOrderByCreatedAtDesc(serviceId: UUID): List<Review>

    @Modifying
    @Query("delete from Review review where review.id.serviceId = :serviceId")
    fun deleteAllByServiceId(serviceId: UUID)
}