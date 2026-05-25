package dev.jefy.connectpro.portfolio.application.dtos

import java.util.*


/**
 * @author Jôph Yamba
 */
data class CreatePortfolioRequest(
    val userId: UUID,
    val name: String,
    val bio: String,
    val details: String?,
    val contact: ContactData,
    val location: LocationData,
    val socials: List<SocialData>
)

data class UpdatePortfolioRequest(
    val name: String,
    val bio: String,
    val details: String?
)
