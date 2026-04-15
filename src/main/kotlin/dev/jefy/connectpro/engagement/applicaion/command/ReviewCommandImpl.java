package dev.jefy.connectpro.engagement.applicaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewRequest;
import dev.jefy.connectpro.engagement.applicaion.exception.ServiceNotExistOrValidException;
import dev.jefy.connectpro.engagement.domain.Review;
import dev.jefy.connectpro.engagement.domain.repository.ReviewRepository;
import dev.jefy.connectpro.engagement.domain.vo.Rating;
import dev.jefy.connectpro.engagement.domain.vo.ReviewId;
import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.recommandation.RecommandationClient;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
import dev.jefy.connectpro.user.UserClient;
import dev.jefy.connectpro.user.application.dtos.UserData;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */

@NullMarked
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandImpl implements ReviewCommand {

    private final PortfolioClient portfolioClient;
    private final UserClient userClient;
    private final ReviewRepository reviewRepo;
    private final RecommandationClient recommandationClient;
    
    @Override
    public void createOrUpdate(ServiceId serviceId, ReviewRequest request) {

        if (portfolioClient.notExistsAndValidService(serviceId)) {
            throw new ServiceNotExistOrValidException();
        }
        UserData user = userClient.getCurrentUser();
        
        ReviewId id = ReviewId.of(user.id(), serviceId);

        reviewRepo.findById(id)
            .ifPresentOrElse(
                review -> {
                    review.update(Rating.of(request.rating()), request.comment());
                    recommandationClient.trackEvent(EventType.REVIEW, serviceId.value(), TargetType.SERVICE);
                },
                () -> {
                    reviewRepo.save(
                            new Review(user.id(), serviceId, Rating.of(request.rating()), request.comment())
                    );
                    recommandationClient.trackEvent(EventType.REVIEW, serviceId.value(), TargetType.SERVICE);
                }
            );
    }

    @Override
    public void delete(ServiceId serviceId) {
        if (portfolioClient.notExistsAndValidService(serviceId)) {
            throw new ServiceNotExistOrValidException();
        }
        UserData user = userClient.getCurrentUser();

        reviewRepo.deleteById(ReviewId.of(user.id(), serviceId));
        recommandationClient.untrackEvent(EventType.REVIEW, serviceId.value(), TargetType.SERVICE);
    }
}
