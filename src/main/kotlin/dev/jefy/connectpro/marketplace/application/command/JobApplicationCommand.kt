package dev.jefy.connectpro.marketplace.application.command

import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationRequest
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import org.jspecify.annotations.NullMarked

/**
 * @author Jôph Yamba
 */

interface JobApplicationCommand {
    fun apply(request: JobApplicationRequest): JobApplicationId
    fun accept(applicationId: JobApplicationId): JobApplicationId
    fun reject(applicationId: JobApplicationId): JobApplicationId
}
