package dev.jefy.connectpro.marketplace.domain

import dev.jefy.connectpro.marketplace.domain.vo.FeaturedSource
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import jakarta.persistence.*
import java.time.Instant
import java.util.*

/**
 * @author  Jôph Yamba
 */

@Entity
@Table(name = "featured_job_posts")
open class FeaturedJobPost(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    open var id: UUID = UUID.randomUUID(),
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "job_post_id"))
    open var jobPostId: JobPostId,
    @Enumerated(EnumType.STRING)
    open var source: FeaturedSource,
    open var priority: Int? = null,
    open var startAt: Instant,
    open var endAt: Instant,
    open var createdAt: Instant = Instant.now(),
    open var updatedAt: Instant = Instant.now(),
)  {
   

    fun isActive(now: Instant = Instant.now()): Boolean {
        val startOk = startAt.let { !it.isAfter(now) }
        val endOk = endAt.isAfter(now)
        return startOk && endOk
    }

    fun updatePriority(priority: Int) {
        this.priority = priority
        this.updatedAt = Instant.now()
    }

    fun updatePeriod(startAt: Instant, endAt: Instant) {
        this.startAt = startAt
        this.endAt = endAt
        this.updatedAt = Instant.now()
    }
}
