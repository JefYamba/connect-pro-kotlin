package dev.jefy.connectpro.management;

import org.jspecify.annotations.NullMarked;

import java.util.Optional;

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse;
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.management.domain.vo.CategoryId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface ManagementClient {
    boolean notExistsCategory(CategoryId categoryId);
    boolean notExistsAward(AwardId awardId);
    boolean notExistsBadge(BadgeId badgeId);
    CategoryResponse getCategory(CategoryId categoryId);
    Optional<AwardResponse> getAward(AwardId awardId);
}
