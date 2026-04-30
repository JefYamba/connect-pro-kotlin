package dev.jefy.connectpro.marketplace.application.dtos

import dev.jefy.connectpro.marketplace.domain.JobApplication
import dev.jefy.connectpro.shared.domain.vo.JobApplicationStatus
import dev.jefy.connectpro.user.application.dtos.UserData
import java.time.Instant


/**
 * @author Jôph Yamba
 */

data class JobApplicationResponseForEmployer(
    val applicant: UserData,
    val motivation: String,
    val appliedAt: Instant,
    val status: JobApplicationStatus
)

fun JobApplication.toEmployerResponse(applicant: UserData) = JobApplicationResponseForEmployer(
    applicant = applicant,
    motivation = this.motivation,
    appliedAt = this.appliedAt,
    status = this.status
)
