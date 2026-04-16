package dev.jefy.connectpro.management.appliacaion.command

import dev.jefy.connectpro.management.appliacaion.BadgeAlreadyExistsException
import dev.jefy.connectpro.management.appliacaion.BadgeAlreadyInUseException
import dev.jefy.connectpro.management.appliacaion.dtos.BadgeRequest
import dev.jefy.connectpro.management.domain.Badge
import dev.jefy.connectpro.management.domain.repository.BadgeRepository
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.management.domain.vo.HexColor
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.application.exceptions.BadgeNotFoundException
import org.jspecify.annotations.NullMarked
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
class BadgeCommandImpl(
    private val badgeRepo: BadgeRepository,
    private val portfolioClient: PortfolioClient
) : BadgeCommand {

    override fun create(request: BadgeRequest): BadgeId {
        Assert.notNull(request, "request is null")
        
        if (badgeRepo.existsByName(request.name))throw BadgeAlreadyExistsException()

        val badge = Badge(request.name, HexColor.of(request.color), request.description)
        badgeRepo.save(badge)
        return badge.id
    }

    override fun update(badgeId: BadgeId, request: BadgeRequest): BadgeId {
        val badge = getBadge(badgeId)
        badge.update(request.name, HexColor.of(request.color), request.description)
        badgeRepo.save(badge)
        return badge.id
    }

    override fun delete(badgeId: BadgeId) {
        val badge = getBadge(badgeId)
        if (portfolioClient.isBadgeInUse(badgeId)) throw BadgeAlreadyInUseException()
        badgeRepo.delete(badge)
    }

    private fun getBadge(badgeId: BadgeId): Badge = badgeRepo
        .findById(badgeId)
        .orElseThrow { BadgeNotFoundException() }
    
}