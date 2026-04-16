package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.SocialLink
import dev.jefy.connectpro.portfolio.domain.vo.SocialPlatform
import jakarta.validation.constraints.NotBlank

data class SocialLinkData(
    val platform: SocialPlatform,
    @field:NotBlank(message = "name value is required")
    val name: String,
    val url: String?
)
fun SocialLink.toData(): SocialLinkData = SocialLinkData(this.platform, this.name, this.url)