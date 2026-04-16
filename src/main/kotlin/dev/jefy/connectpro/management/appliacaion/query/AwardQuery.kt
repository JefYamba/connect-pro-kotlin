package dev.jefy.connectpro.management.appliacaion.query

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse
import dev.jefy.connectpro.management.domain.vo.AwardId

/**
 * @author Jôph Yamba
 */
interface AwardQuery {
    fun get(awardId: AwardId): AwardResponse
    fun getAll(): List<AwardResponse>
}
