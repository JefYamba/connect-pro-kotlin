package dev.jefy.connectpro.recommandation.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import dev.jefy.connectpro.recommandation.domain.EventTracking
import dev.jefy.connectpro.recommandation.domain.repository.EventTrackingRepository
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.application.dtos.UserData
import dev.jefy.connectpro.user.domain.vo.UserId
import java.util.UUID

@Service
@Transactional
class EventTrackingServiceImpl(
    private val eventTrackingRepository: EventTrackingRepository,
    private val userClient: UserClient
) : EventTrackingService {

    override fun trackEvent(eventType: EventType, targetId: UUID, targetType: TargetType) {
        val currentUser: UserData = userClient.getCurrentUser()
        val event = EventTracking(UserId.of(currentUser.id), eventType, targetId, targetType)
        eventTrackingRepository.save(event)
    }

    override fun untrackEvent(eventType: EventType, targetId: UUID, targetType: TargetType) {
        val currentUser: UserData = userClient.getCurrentUser()
        eventTrackingRepository.deleteByUserIdAndEventTypeAndTargetIdAndTargetType(
            UserId.of(currentUser.id),
            eventType,
            targetId,
            targetType
        )
    }
}