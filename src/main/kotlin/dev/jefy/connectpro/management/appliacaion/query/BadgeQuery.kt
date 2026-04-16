package dev.jefy.connectpro.management.appliacaion.query

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse
import dev.jefy.connectpro.management.domain.vo.BadgeId
import org.jspecify.annotations.NullMarked

/**
 * @author Jôph Yamba
 */

interface BadgeQuery {
    fun get(badgeId: BadgeId): BadgeResponse
    fun gatAll() : List<BadgeResponse>
}
