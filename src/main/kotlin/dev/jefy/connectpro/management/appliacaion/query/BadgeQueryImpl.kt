package dev.jefy.connectpro.management.appliacaion.query

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse
import dev.jefy.connectpro.management.appliacaion.dtos.toResponse
import dev.jefy.connectpro.management.domain.repository.BadgeRepository
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.application.exceptions.BadgeNotFoundException
import org.jspecify.annotations.NullMarked
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Supplier

/**
 * @author Jôph Yamba
 */
@Service
@Transactional(readOnly = true)
class BadgeQueryImpl(private val badgeRepo: BadgeRepository) : BadgeQuery {

    override fun get(badgeId: BadgeId): BadgeResponse = badgeRepo
        .findById(badgeId)
        .map{ it.toResponse() }
        .orElseThrow { BadgeNotFoundException() }
    
    override fun gatAll(): MutableList<BadgeResponse> = badgeRepo
        .findAll()
        .stream()
        .map{ it.toResponse() }
        .toList()
}
