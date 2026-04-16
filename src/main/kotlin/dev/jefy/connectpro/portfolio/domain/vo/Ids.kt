package dev.jefy.connectpro.portfolio.domain.vo

import jakarta.persistence.Embeddable
import java.util.*

/**
 * @author  Jôph Yamba
 */
@Embeddable
data class FAQId(var value: UUID)  {
    
    companion object {
        fun generate(): FAQId = FAQId(UUID.randomUUID())
        fun of(faqId: UUID): FAQId = FAQId(faqId)
    }
}

@Embeddable
data class PortfolioId(var value: UUID) {

    companion object {
        fun generate(): PortfolioId = PortfolioId(UUID.randomUUID())
        fun of(portfolioId: UUID): PortfolioId = PortfolioId(portfolioId)
    }
}
@Embeddable
data class ProjectId(var value: UUID) {

    companion object {
        fun generate(): ProjectId = ProjectId(UUID.randomUUID())
        fun of(projectId: UUID): ProjectId = ProjectId(projectId)
    }
}
@Embeddable
data class ServiceId(var value: UUID) {

    companion object {
        fun generate(): ServiceId = ServiceId(UUID.randomUUID())
        fun of(serviceId: UUID): ServiceId = ServiceId(serviceId)
    }
}

@Embeddable
data class SocialLinkId(var value: UUID) {

    companion object {
        fun generate(): SocialLinkId = SocialLinkId(UUID.randomUUID())
        fun of(socialLinkId: UUID): SocialLinkId = SocialLinkId(socialLinkId)
    }
}

@Embeddable
data class JobPostId(var value: UUID) {
    companion object {
        fun generate(): JobPostId = JobPostId(UUID.randomUUID())
        fun of(value: UUID): JobPostId = JobPostId(value)
    }
}