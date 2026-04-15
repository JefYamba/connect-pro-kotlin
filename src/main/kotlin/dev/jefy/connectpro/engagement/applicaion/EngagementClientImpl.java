package dev.jefy.connectpro.engagement.applicaion;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import dev.jefy.connectpro.engagement.EngagementClient;
import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewResponse;
import dev.jefy.connectpro.engagement.domain.repository.LikeRepository;
import dev.jefy.connectpro.engagement.domain.repository.ReviewRepository;
import dev.jefy.connectpro.engagement.domain.vo.LikeId;
import dev.jefy.connectpro.marketplace.applicaion.dtos.ServiceReviewData;
import dev.jefy.connectpro.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
@RequiredArgsConstructor
public class EngagementClientImpl implements EngagementClient {

    private final LikeRepository likeRepo;
    private final ReviewRepository reviewRepo;

    @Override
    public long countLike(ServiceId serviceId) {
        return likeRepo.countByIdServiceId(serviceId.value());
    }

    @Override
    public boolean hasLiked(ServiceId serviceId, UserId userId) {
        return likeRepo.existsById(LikeId.of(userId, serviceId));
    }
    

    @Override
    public List<ReviewResponse> recentReviews(ServiceId serviceId) {
        return reviewRepo
                .findTop10ByIdServiceIdOrderByCreatedAtDesc(serviceId.value())
                .stream()
                .map(ReviewResponse::fromDomain)
                .toList();
    }
    

    @Override
    public ServiceReviewData getReviewData(ServiceId id) {
        Double avg = reviewRepo.averageRating(id.value());
        long nbrReviews = reviewRepo.countByIdServiceId(id.value());
        return new ServiceReviewData(
                avg != null ? avg : 0.0, 
                (int) nbrReviews
        );
    }
}
