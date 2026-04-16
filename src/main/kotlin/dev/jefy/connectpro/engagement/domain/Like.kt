package dev.jefy.connectpro.engagement.domain


import dev.jefy.connectpro.engagement.domain.vo.LikeId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.*
import java.time.Instant

/**
 * @author Jôph Yamba
 */
@Entity
@Table(name = "likes")
open class Like(reviewerId: UserId, serviceId: ServiceId) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: LikeId = LikeId.of(reviewerId, serviceId)
    protected set

    var createdAt: Instant = Instant.now()
    protected set
}
