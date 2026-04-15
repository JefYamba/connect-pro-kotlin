package dev.jefy.connectpro.engagement.applicaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.jefy.connectpro.engagement.applicaion.exception.ServiceNotExistOrValidException;
import dev.jefy.connectpro.engagement.domain.Like;
import dev.jefy.connectpro.engagement.domain.repository.LikeRepository;
import dev.jefy.connectpro.engagement.domain.vo.LikeId;
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
public class LikeCommandImpl implements LikeCommand {
    private final PortfolioClient portfolioClient;
    private final UserClient userClient;
    private final LikeRepository likeRepo;
    private final RecommandationClient recommandationClient;

    @Override
    public void like(ServiceId serviceId) {
        if (portfolioClient.notExistsAndValidService(serviceId)) {
            throw new ServiceNotExistOrValidException();
        }
        UserData user = userClient.getCurrentUser();
        LikeId id = LikeId.of(user.id(), serviceId);
        // idempotent
        if (likeRepo.existsById(id)) return;
        likeRepo.save(new Like(user.id(), serviceId));
        recommandationClient.trackEvent(EventType.LIKE, serviceId.value(), TargetType.SERVICE);
    }

    @Override
    public void unlike(ServiceId serviceId) {
        if (portfolioClient.notExistsAndValidService(serviceId)) {
            throw new ServiceNotExistOrValidException();
        }
        UserData user = userClient.getCurrentUser();
        LikeId id = LikeId.of(user.id(), serviceId);
        // idempotent
        if (!likeRepo.existsById(id)) return;
        likeRepo.deleteById(id);
        recommandationClient.untrackEvent(EventType.LIKE, serviceId.value(), TargetType.SERVICE);
    }
}
