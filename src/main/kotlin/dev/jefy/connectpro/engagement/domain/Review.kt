package dev.jefy.connectpro.engagement.domain

import dev.jefy.connectpro.engagement.domain.vo.Rating
import dev.jefy.connectpro.engagement.domain.vo.ReviewId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.*
import java.time.Instant

/**
 * @author Jôph Yamba
 */
@Entity
@Table(name = "reviews")
open class Review(reviewerId: UserId, serviceId: ServiceId, rating: Rating, comment: String) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: ReviewId = ReviewId.of(reviewerId, serviceId)
    protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "rating"))
    var rating: Rating = rating
    protected set

    @Column(nullable = false, updatable = false)
    var comment: String = comment
    protected set
        
    var createdAt: Instant = Instant.now()
    protected set

    init {
        require(comment.isNotBlank()) { "comment cannot be empty" }
    }

    fun update(rating: Rating, comment: String) {
        require(comment.isNotBlank()) { "comment cannot be empty" }
        this.rating = rating
        this.comment = comment
    }
}
