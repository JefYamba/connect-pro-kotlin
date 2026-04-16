package dev.jefy.connectpro.management

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.management.domain.vo.CategoryId
import java.util.*

/**
 * @author Jôph Yamba
 */

interface ManagementClient {
    fun notExistsCategory(categoryId: CategoryId): Boolean
    fun notExistsAward(awardId: AwardId): Boolean
    fun notExistsBadge(badgeId: BadgeId): Boolean
    fun getCategory(categoryId: CategoryId): CategoryResponse
    fun getAward(awardId: AwardId): Optional<AwardResponse>
}
