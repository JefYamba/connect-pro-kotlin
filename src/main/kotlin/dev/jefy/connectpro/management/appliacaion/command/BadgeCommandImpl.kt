package dev.jefy.connectpro.management.appliacaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeRequest;
import dev.jefy.connectpro.management.domain.Badge;
import dev.jefy.connectpro.management.domain.repositoty.BadgeRepository;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.management.domain.vo.HexColor;
import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.shared.application.exceptions.ResourceAlreadyExists;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
@RequiredArgsConstructor
public class BadgeCommandImpl implements BadgeCommand {
    private final BadgeRepository badgeRepo;
    private final PortfolioClient portfolioClient;
    
    @Override
    public BadgeId create(BadgeRequest request) {
        Assert.notNull(request, "request is null");
        if (!badgeRepo.existsByName(request.name())){
            throw new ResourceAlreadyExists("badge with name " + request.name() + " already exists");
        }
        Badge badge = new Badge(request.name(), HexColor.of(request.color()), request.description());
        badgeRepo.save(badge);
        return badge.getId();
    }

    @Override
    public BadgeId update(BadgeId badgeId, BadgeRequest request) {
        Assert.notNull(request, "request is null");
        Badge badge = getBadge(badgeId);
        badge.update(request.name(), HexColor.of(request.color()), request.description());
        badgeRepo.save(badge);
        return badge.getId();
    }

    @Override
    public void delete(BadgeId badgeId) {
        Badge badge = getBadge(badgeId);
        if (portfolioClient.isBadgeInUse(badgeId)) {
            throw new IllegalStateException(String.format("badge with id %s is already in use", badgeId));
        }
        badgeRepo.delete(badge);
    }

    private Badge getBadge(BadgeId badgeId) {
        Assert.notNull(badgeId, "badgeId is null");
        return badgeRepo.findById(badgeId)
                .orElseThrow(()-> new ResourceNotFound("badge with id " + badgeId + " not found"));
    }
}
