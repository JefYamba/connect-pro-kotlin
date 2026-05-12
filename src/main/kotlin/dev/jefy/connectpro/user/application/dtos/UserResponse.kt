package dev.jefy.connectpro.user.application.dtos

import dev.jefy.connectpro.shared.application.dtos.PortfolioData
import dev.jefy.connectpro.user.domain.model.AuthUser
import dev.jefy.connectpro.user.domain.vo.UserRole
import java.util.*

data class UserResponse(
    val id: UUID,
    val email: String,
    val name: String?,
    val image: String?,
    val role: UserRole,
    val portfolio: PortfolioData?
)

fun AuthUser.toUserResponse(): UserResponse = UserResponse(
    id = this.id,
    email = this.email,
    name = this.fullname,
    image = this.image,
    role = this.role,
    portfolio = this.portfolio
)