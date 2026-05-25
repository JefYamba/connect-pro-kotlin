package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.portfolio.application.dtos.SocialData
import dev.jefy.connectpro.portfolio.domain.vo.SocialId
import dev.jefy.connectpro.portfolio.domain.vo.SocialPlatform
import jakarta.persistence.*

@Entity
@Table(
    name = "socials",
    uniqueConstraints = [UniqueConstraint(columnNames = ["portfolio_id", "platform"])]
)
open class Social(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: SocialId = SocialId.generate(),
    @ManyToOne(optional = false)
    var portfolio: Portfolio,
    @Enumerated(EnumType.STRING)
    var platform: SocialPlatform,
    @Column(unique = true)
    var url: String,
){
    
    fun update(data: SocialData) {
        check(portfolio.socials.none { it.platform == data.platform }){ "Social Link with platform already exists"}
        url = data.url
    }
}