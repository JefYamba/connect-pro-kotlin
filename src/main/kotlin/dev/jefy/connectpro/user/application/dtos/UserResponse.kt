package dev.jefy.connectpro.user.application.dtos

import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData
import dev.jefy.connectpro.user.domain.model.AuthUser
import dev.jefy.connectpro.user.domain.vo.UserRole
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val email: String,
    val name: String?,
    val imageUrl: String?,
    val role: UserRole,
    val portfolio: PortfolioSummaryData?
)

fun AuthUser.toUserResponse(): UserResponse = UserResponse(
    id = this.id,
    email = this.email,
    name = this.fullname,
    imageUrl = this.imageUrl,
    role = this.role,
    portfolio = this.portfolio
)