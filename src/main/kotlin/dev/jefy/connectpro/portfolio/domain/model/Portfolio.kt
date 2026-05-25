package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.application.exceptions.SocialNotFoundException
import dev.jefy.connectpro.portfolio.domain.vo.*
import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "portfolios")
open class Portfolio(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: PortfolioId = PortfolioId.generate(),
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    var userId: UserId,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var bio: String,
    @Column(columnDefinition = "TEXT")
    var details: String?,
    var coverImage: String? = null,
    @Enumerated(EnumType.STRING)
    var status: PortfolioStatus = PortfolioStatus.ACTIVE,
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "badge_id"))
    var badgeId: BadgeId? = null,
    var createdAt: Instant = Instant.now(),
    @Embedded
    var contact: Contact,
    @Embedded
    var location: Location,
    @OneToMany(mappedBy = "portfolio", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var socials: MutableSet<Social> = mutableSetOf(),
    
)  {
    init {
        require(name.isNotBlank()) { "name must not be blank" }
        require(bio.isNotBlank()) { "short description must not be blank" }
    }

    fun activate() { this.status = PortfolioStatus.ACTIVE }

    fun deactivate() { this.status = PortfolioStatus.INACTIVE }

    fun block() { this.status = PortfolioStatus.BLOCKED }

    fun update(name: String, bio: String, details: String?,) {
        require(name.isNotBlank()) { "name must not be blank" }
        require(bio.isNotBlank()) { "short description must not be blank" }
        this.name = name
        this.bio = bio
        this.details = details
    }

    fun updateContact(contact: Contact) { this.contact = contact }

    fun updateLocation(location: Location) { this.location = location }

    fun isActive(): Boolean = this.status == PortfolioStatus.ACTIVE
    
    fun addSocial(social: Social) { this.socials.add(social) }

    fun deleteSocial(socialId: SocialId) {
        val removed = socials.removeIf { it.id == socialId }
        if (!removed) throw SocialNotFoundException()
    }
    fun setCoverImage(image: Image) { coverImage = image.value }
    fun deleteCoverImage() { coverImage = null }
    fun addBadgeId(badgeId: BadgeId) { this.badgeId = badgeId }
}