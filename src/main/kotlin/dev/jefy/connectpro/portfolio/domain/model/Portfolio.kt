package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.application.dtos.SocialLinkData
import dev.jefy.connectpro.portfolio.application.exceptions.SocialLinkNotFoundException
import dev.jefy.connectpro.portfolio.domain.vo.*
import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "portfolios")
open class Portfolio(
    userId: UserId,
    type: PortfolioType,
    generalInfo: GeneralInfo,
    professionalInfo: ProfessionalInfo,
    contactInfo: ContactInfo,
    locationInfo: LocationInfo,
    socialLinks: List<SocialLinkData>
    
)  {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: PortfolioId = PortfolioId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    var userId: UserId = userId
        protected set

    @Enumerated(EnumType.STRING)
    var type: PortfolioType = type
        protected set

    @Enumerated(EnumType.STRING)
    var status: PortfolioStatus = PortfolioStatus.ACTIVE
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "badge_id"))
    var badgeId: BadgeId? = null
        protected set

    var createdAt: Instant = Instant.now()
        protected set

    @Embedded
    var generalInfo: GeneralInfo = generalInfo
        protected set

    @Embedded
    var professionalInfo: ProfessionalInfo = professionalInfo
        protected set

    @Embedded
    var contactInfo: ContactInfo = contactInfo
        protected set

    @Embedded
    var locationInfo: LocationInfo = locationInfo
        protected set

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var socialLinks: MutableList<SocialLink> =  run {
        val platforms = mutableSetOf<SocialPlatform>()
        socialLinks.map { data ->
            check(platforms.add(data.platform)) { "Duplicate social link platform: ${data.platform}" }
            SocialLink(this, data)
        }.toMutableList()
    }
        protected set

    fun changeType(newType: PortfolioType) { this.type = newType }

    fun activate() { this.status = PortfolioStatus.ACTIVE }

    fun deactivate() { this.status = PortfolioStatus.INACTIVE }

    fun block() { this.status = PortfolioStatus.BLOCKED }

    fun updateGeneralInfo(generalInfo: GeneralInfo) { this.generalInfo = generalInfo }

    fun updateProfessionalInfo(professionalInfo: ProfessionalInfo) {this.professionalInfo = professionalInfo }

    fun updateContactInfo(contactInfo: ContactInfo) { this.contactInfo = contactInfo }

    fun updateLocationInfo(locationInfo: LocationInfo) { this.locationInfo = locationInfo }

    fun isActive(): Boolean = this.status == PortfolioStatus.ACTIVE
    
    fun addSocialLink(socialLink: SocialLinkData) { this.socialLinks.add(SocialLink(this, socialLink)) }

    fun deleteSocialLink(socialLinkId: SocialLinkId) {
        val removed = socialLinks.removeIf { it.id == socialLinkId }

        if (!removed) throw SocialLinkNotFoundException()
    }

    fun setCoverImageUrl(imageUrl: ImageUrl) { this.generalInfo = this.generalInfo.copy(coverImageUrl = imageUrl) }

    fun deleteCoverImageUrl() { this.generalInfo = this.generalInfo.copy(coverImageUrl = null) }

    fun addBadgeId(badgeId: BadgeId) { this.badgeId = badgeId }
}