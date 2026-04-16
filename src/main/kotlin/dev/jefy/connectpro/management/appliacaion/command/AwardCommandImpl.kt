package dev.jefy.connectpro.management.appliacaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import dev.jefy.connectpro.management.appliacaion.dtos.AwardRequest;
import dev.jefy.connectpro.management.domain.Award;
import dev.jefy.connectpro.management.domain.repositoty.AwardRepository;
import dev.jefy.connectpro.management.domain.vo.AwardId;
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
public class AwardCommandImpl implements AwardCommand {
    private final AwardRepository awardRepo;
    private final PortfolioClient portfolioClient;

    @Override
    public AwardId create(AwardRequest request) {
        Assert.notNull(request, "request is null");
        if (!awardRepo.existsByName(request.name())) {
            throw new ResourceAlreadyExists("award with name " + request.name() + " already exists");
        }
        Award award = new Award(request.name(), HexColor.of(request.color()), request.description());
        awardRepo.save(award);
        return award.getId();
    }

    @Override
    public AwardId update(AwardId awardId, AwardRequest request) {
        Assert.notNull(request, "request is null");
        Award award = getAward(awardId);
        award.update(request.name(), HexColor.of(request.color()), request.description());
        awardRepo.save(award);
        return award.getId();
    }

    @Override
    public void delete(AwardId awardId) {
        Award award = getAward(awardId);
        if (portfolioClient.isAwardInUse(awardId)) {
            throw new IllegalStateException(String.format("award with id %s is already in use", awardId));
        }
        awardRepo.delete(award);
    }

    private Award getAward(AwardId awardId) {
        Assert.notNull(awardId, "awardId is null");
        return awardRepo.findById(awardId)
                .orElseThrow(()-> new ResourceNotFound("award with id " + awardId + " not found"));
    }
}
