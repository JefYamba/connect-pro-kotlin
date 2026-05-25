package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.Social
import dev.jefy.connectpro.portfolio.domain.vo.SocialPlatform
import jakarta.validation.constraints.NotBlank

data class SocialData(
    val platform: SocialPlatform,
    @field:NotBlank(message = "url is required")
    val url: String
)
fun Social.toData(): SocialData = SocialData(this.platform, this.url)