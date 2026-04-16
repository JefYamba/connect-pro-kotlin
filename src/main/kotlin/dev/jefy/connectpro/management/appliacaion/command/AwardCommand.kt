package dev.jefy.connectpro.management.appliacaion.command

import dev.jefy.connectpro.management.appliacaion.dtos.AwardRequest
import dev.jefy.connectpro.management.domain.vo.AwardId
import org.jspecify.annotations.NullMarked


/**
 * @author Jôph Yamba
 */

interface AwardCommand {
    fun create(request: AwardRequest): AwardId
    fun update(awardId: AwardId, request: AwardRequest): AwardId
    fun delete(awardId: AwardId)
}

