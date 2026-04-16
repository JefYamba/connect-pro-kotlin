package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.Availability
import dev.jefy.connectpro.portfolio.domain.vo.ProfessionalInfo


/**
 * @author Jôph Yamba
 */
@JvmRecord
data class ProfessionalInfoRequest(
    val activeYears: Int?,
    val numberOfEmployees: Int?,
    val availabilities: List<Availability>
) {
    fun toDomain(): ProfessionalInfo {
        return ProfessionalInfo(
            numberOfEmployees = this.numberOfEmployees,
            activeYears = this.activeYears,
            availabilities = this.availabilities
        )
    }
}
