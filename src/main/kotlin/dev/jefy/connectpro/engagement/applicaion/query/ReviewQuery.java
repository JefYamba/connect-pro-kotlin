package dev.jefy.connectpro.engagement.applicaion.query;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewResponse;
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface ReviewQuery {
    @Nullable ReviewResponse findServiceReviewForCurrentUser(ServiceId serviceId);
    List<ReviewResponse> findByService(ServiceId serviceId);
}
