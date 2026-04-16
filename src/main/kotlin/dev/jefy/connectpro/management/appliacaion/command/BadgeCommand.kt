package dev.jefy.connectpro.management.appliacaion.command

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeRequest
import dev.jefy.connectpro.management.domain.vo.BadgeId
import org.jspecify.annotations.NullMarked

/**
 * @author Jôph Yamba
 */
@NullMarked
interface BadgeCommand {
    fun create(request: BadgeRequest): BadgeId
    fun update(badgeId: BadgeId, request: BadgeRequest): BadgeId
    fun delete(badgeId: BadgeId)
}
