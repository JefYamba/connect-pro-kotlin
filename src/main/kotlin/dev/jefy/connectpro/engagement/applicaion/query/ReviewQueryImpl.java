package dev.jefy.connectpro.engagement.applicaion.query;


import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewResponse;
import dev.jefy.connectpro.engagement.domain.repository.ReviewRepository;
import dev.jefy.connectpro.engagement.domain.vo.ReviewId;
import dev.jefy.connectpro.user.UserClient;
import dev.jefy.connectpro.user.application.dtos.UserData;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */

@NullMarked
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewQueryImpl implements ReviewQuery {
    private final UserClient userClient;
    private final ReviewRepository reviewRepo;
    @Override
    public @Nullable ReviewResponse findServiceReviewForCurrentUser(ServiceId serviceId) {
        UserData user = userClient.getCurrentUser();
        
        return reviewRepo.findById(ReviewId.of(user.id(), serviceId))
                .map(ReviewResponse::fromDomain)
                .orElse(null);
    }

    @Override
    public List<ReviewResponse> findByService(ServiceId serviceId) {
        return reviewRepo.findByIdServiceId(serviceId.value())
                .stream()
                .map(ReviewResponse::fromDomain)
                .toList();
    }
}
