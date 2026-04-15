package dev.jefy.connectpro.management.appliacaion;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import dev.jefy.connectpro.management.ManagementClient;
import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse;
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.management.domain.repositoty.AwardRepository;
import dev.jefy.connectpro.management.domain.repositoty.BadgeRepository;
import dev.jefy.connectpro.management.domain.repositoty.CategoryRepository;
import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
@RequiredArgsConstructor
public class ManagementClientImpl implements ManagementClient {
    private final CategoryRepository categoryRepo;
    private final AwardRepository awardRepo;
    private final BadgeRepository badgeRepo;
    
    @Override
    public boolean notExistsCategory(CategoryId categoryId) {
        return !categoryRepo.existsById(categoryId);
    }

    @Override
    public boolean notExistsAward(AwardId awardId) {
        return !awardRepo.existsById(awardId);
    }

    @Override
    public boolean notExistsBadge(BadgeId badgeId) {
        return !badgeRepo.existsById(badgeId);
    }

    @Override
    public CategoryResponse getCategory(CategoryId categoryId) {
        return categoryRepo.findById(categoryId)
                .map(CategoryResponse::fromDomain)
                .orElseThrow(()-> new ResourceNotFound(
                        "category with id: %s not found"
                        .formatted(categoryId.value())
                ));
    }

    @Override
    public Optional<AwardResponse> getAward(AwardId awardId) {
        return awardRepo.findById(awardId).map(AwardResponse::from);
    }
}
