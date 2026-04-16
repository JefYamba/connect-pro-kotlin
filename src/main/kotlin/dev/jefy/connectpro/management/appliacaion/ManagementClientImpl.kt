package dev.jefy.connectpro.management.appliacaion

import dev.jefy.connectpro.management.ManagementClient
import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.management.appliacaion.dtos.toResponse
import dev.jefy.connectpro.management.domain.repository.AwardRepository
import dev.jefy.connectpro.management.domain.repository.BadgeRepository
import dev.jefy.connectpro.management.domain.repository.CategoryRepository
import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.application.exceptions.CategoryNotFoundException
import org.jspecify.annotations.NullMarked
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

/**
 * @author Jôph Yamba
 */
@Service
@Transactional
class ManagementClientImpl(
    private val categoryRepo: CategoryRepository,
    private val awardRepo: AwardRepository,
    private val badgeRepo: BadgeRepository
) : ManagementClient {

    override fun notExistsCategory(categoryId: CategoryId): Boolean = !categoryRepo.existsById(categoryId)

    override fun notExistsAward(awardId: AwardId): Boolean = !awardRepo.existsById(awardId)

    override fun notExistsBadge(badgeId: BadgeId): Boolean = !badgeRepo.existsById(badgeId)

    override fun getCategory(categoryId: CategoryId): CategoryResponse = categoryRepo
        .findById(categoryId)
        .map{ it.toResponse() }
        .orElseThrow { CategoryNotFoundException() }

    override fun getAward(awardId: AwardId): Optional<AwardResponse> = awardRepo
        .findById(awardId)
        .map { it.toResponse() }
}