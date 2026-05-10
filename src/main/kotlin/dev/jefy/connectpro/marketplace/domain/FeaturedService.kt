package dev.jefy.connectpro.marketplace.domain

import dev.jefy.connectpro.marketplace.domain.vo.FeaturedSource
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.Instant
import java.util.*

/**
 * @author  Jôph Yamba
 */
@Embeddable
data class FeaturedServiceId(var value: UUID){
    companion object {
        fun generate(): FeaturedServiceId = FeaturedServiceId(UUID.randomUUID())
    }
}

@Entity
@Table(name = "featured_services")
open class FeaturedService(
    serviceId: ServiceId,
    source: FeaturedSource,
    priority: Int?,
    startAt: Instant,
    endAt: Instant
)  {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    open var id: FeaturedServiceId = FeaturedServiceId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "service_id"))
    open var serviceId: ServiceId = serviceId
        protected set
    open var source: FeaturedSource = source
        protected set
    open var priority: Int? = priority
        protected set
    open var startAt: Instant = startAt
        protected set
    open var endAt: Instant = endAt
        protected set
    open var createdAt: Instant = Instant.now()
        protected set
    open var updatedAt: Instant = Instant.now()
        protected set
    
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