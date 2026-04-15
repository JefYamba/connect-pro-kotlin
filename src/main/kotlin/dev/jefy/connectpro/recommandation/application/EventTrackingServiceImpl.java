package dev.jefy.connectpro.recommandation.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import dev.jefy.connectpro.recommandation.domain.EventTracking;
import dev.jefy.connectpro.recommandation.domain.repository.EventTrackingRepository;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
import dev.jefy.connectpro.user.UserClient;
import dev.jefy.connectpro.user.application.dtos.UserData;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@Transactional
@RequiredArgsConstructor
public class EventTrackingServiceImpl implements EventTrackingService {
    private final EventTrackingRepository eventTrackingRepository;
    private final UserClient userClient;

    @Override
    public void trackEvent(EventType eventType, UUID targetId, TargetType targetType) {
        UserData currentUser = userClient.getCurrentUser();
        EventTracking event = new EventTracking(currentUser.id(), eventType, targetId, targetType);
        eventTrackingRepository.save(event);
    }

    @Override
    public void untrackEvent(EventType eventType, UUID targetId, TargetType targetType) {
        UserData currentUser = userClient.getCurrentUser();
        eventTrackingRepository.deleteByUserIdAndEventTypeAndTargetIdAndTargetType(currentUser.id(), eventType, targetId, targetType);
    }
}
