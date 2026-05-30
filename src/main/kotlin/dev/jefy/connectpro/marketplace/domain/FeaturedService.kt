package dev.jefy.connectpro.marketplace.domain

import dev.jefy.connectpro.marketplace.domain.vo.FeaturedSource
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import jakarta.persistence.*
import java.time.Instant
import java.util.*

/**
 * @author  Jôph Yamba
 */

@Entity
@Table(name = "featured_services")
open class FeaturedService(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: UUID = UUID.randomUUID(),
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "service_id"))
    var serviceId: ServiceId,
    @Enumerated(EnumType.STRING)
    var source: FeaturedSource,
    var priority: Int? = null,
    var startAt: Instant,
    var endAt: Instant,
    var createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now(),
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