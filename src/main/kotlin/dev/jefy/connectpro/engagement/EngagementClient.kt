package dev.jefy.connectpro.engagement;

import org.jspecify.annotations.NullMarked;

import java.util.List;

import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewResponse;
import dev.jefy.connectpro.marketplace.applicaion.dtos.ServiceReviewData;
import dev.jefy.connectpro.user.domain.vo.UserId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface EngagementClient {
    long countLike(ServiceId serviceId);
    boolean hasLiked(ServiceId serviceId, UserId userId);
    List<ReviewResponse> recentReviews(ServiceId serviceId);
    ServiceReviewData getReviewData(ServiceId id);
}
