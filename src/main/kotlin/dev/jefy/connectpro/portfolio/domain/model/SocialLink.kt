package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.portfolio.application.dtos.SocialLinkData
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId
import dev.jefy.connectpro.portfolio.domain.vo.SocialPlatform
import jakarta.persistence.*

@Entity
@Table(
    name = "social_links",
    uniqueConstraints = [UniqueConstraint(columnNames = ["portfolio_id", "platform"])]
)
open class SocialLink(portfolio: Portfolio, data: SocialLinkData){

    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: SocialLinkId = SocialLinkId.generate()
        protected set

    @ManyToOne(optional = false)
    var portfolio: Portfolio = portfolio
     protected set

    @Enumerated(EnumType.STRING)
    var platform: SocialPlatform = data.platform
        protected set

    @Column(nullable = false)
    var name: String = data.name
        protected set

    @Column(nullable = false, unique = true)
    var url: String? = data.url
        protected set

    init {
        check(portfolio.socialLinks.none { it.platform == data.platform }){ "Social Link with platform already exists"}
    }

    fun update(data: SocialLinkData) {
        check(portfolio.socialLinks.none { it.platform == data.platform }){ "Social Link with platform already exists"}
        name = data.name
        url = data.url
    }
}