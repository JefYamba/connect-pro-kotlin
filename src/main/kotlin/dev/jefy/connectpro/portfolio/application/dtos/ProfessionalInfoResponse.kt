package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.Availability
import dev.jefy.connectpro.portfolio.domain.vo.ProfessionalInfo

/**
 * @author Jôph Yamba
 */
@JvmRecord
data class ProfessionalInfoResponse(
    val activeYears: Int?,
    val numberOfEmployees: Int?,
    val projects: List<ProjectResponse>,
    val availabilities: List<Availability>
)

fun ProfessionalInfo.toResponse(projects: List<ProjectResponse>) =  ProfessionalInfoResponse(
        activeYears = this.activeYears,
        numberOfEmployees = this.numberOfEmployees,
        projects = projects,
        availabilities = this.availabilities
)