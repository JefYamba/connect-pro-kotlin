package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse
import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.ServiceListingResponse
import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioStatus
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import java.time.Instant
import java.util.*

/**
 * @author Jôph Yamba
 */

data class PortfolioResponse(
    val id: UUID,
    val userId: UUID,
    val name: String,
    val bio: String,
    val details: String?,
    val coverImage: String?,
    val status: PortfolioStatus,
    val badge: BadgeResponse?,
    val createdAt: Instant,
    val contact: ContactData,
    val location: LocationData,
    val socials: List<SocialData>,
    val projects: List<ProjectResponse>,
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
    name = this.name,
    bio = this.bio,
    details = this.details,
    coverImage = resolver.resolve(this.coverImage),
    status = this.status,
    badge = badge,
    createdAt = this.createdAt,
    contact = this.contact.toData(),
    location = this.location.toData(),
    socials = this.socials.map{ it.toData() }.toList(),
    projects = projects,
    services = services,
    jobPosts = jobPosts
)