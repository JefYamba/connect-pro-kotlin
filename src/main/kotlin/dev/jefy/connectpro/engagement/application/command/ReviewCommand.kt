package dev.jefy.connectpro.engagement.application.command

import dev.jefy.connectpro.engagement.application.dtos.ReviewRequest
import dev.jefy.connectpro.engagement.presentation.ReviewOperationResult
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId

/**
 * @author Jôph Yamba
 */
interface ReviewCommand {
    fun createOrUpdate(serviceId: ServiceId, request: ReviewRequest): ReviewOperationResult
    fun delete(serviceId: ServiceId)
}
