package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType
import java.util.*


/**
 * @author Jôph Yamba
 */
data class PortfolioRequest(
    val userId: UUID,
    val type: PortfolioType,
    val generalInfo: GeneralInfoRequest,
    val professionalInfo: ProfessionalInfoRequest,
    val contactInfo: ContactInfoData,
    val locationInfo: LocationInfoData,
    val socialLinks: List<SocialLinkData>
)
