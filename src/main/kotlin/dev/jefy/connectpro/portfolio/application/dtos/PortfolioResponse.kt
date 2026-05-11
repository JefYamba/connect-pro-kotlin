package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse
import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.ServiceListingResponse
import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioStatus
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import java.time.Instant
import java.util.*

/**
 * @author Jôph Yamba
 */
@JvmRecord
data class PortfolioResponse(
    val id: UUID,
    val userId: UUID,
    val type: PortfolioType,
    val status: PortfolioStatus,
    val badge: BadgeResponse?,
    val createdAt: Instant,
    val generalInfo: GeneralInfoResponse,
    val professionalInfo: ProfessionalInfoResponse,
    val contactInfo: ContactInfoData,
    val locationInfo: LocationInfoData,
    val socialLinks: List<SocialLinkData>,
    val services: List<ServiceListingResponse>,
    val jobPosts: List<JobPostListingResponse>
)

fun Portfolio.toResponse(
    badge: BadgeResponse?,
    services: List<ServiceListingResponse>,
    jobPosts: List<JobPostListingResponse>,
    projects: List<ProjectResponse>,
    resolver: ImageUrlResolver
): PortfolioResponse = PortfolioResponse(
    id = this.id.value,
    userId = this.userId.value,
    type = this.type,
    status = this.status,
    badge = badge,
    createdAt = this.createdAt,
    generalInfo = this.generalInfo.toResponse(resolver),
    professionalInfo = this.professionalInfo.toResponse(projects),
    contactInfo = this.contactInfo.toData(),
    locationInfo = this.locationInfo.toData(),
    socialLinks = this.socialLinks.map{ it.toData() }.toList(),
    services = services,
    jobPosts = jobPosts
)