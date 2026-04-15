package dev.jefy.connectpro.user.application.dtos

import dev.jefy.connectpro.user.domain.model.AuthUser
import dev.jefy.connectpro.user.domain.model.User
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.UserId
import dev.jefy.connectpro.user.domain.vo.UserRole
import java.util.UUID

data class UserData(
    val id: UUID,
    val email: String,
    val name: String,
    val role: UserRole,
    val imageUrl: String? = null
)

fun User.toUserData(): UserData = UserData(
    id = this.id.value,
    email = this.email.value,
    name = this.name,
    role = this.role,
    imageUrl = this.profileImage?.value
)

fun AuthUser.toUserData(): UserData = UserData(
    id = this.id,
    email = this.email,
    name = this.name,
    role = this.role,
    imageUrl = this.imageUrl
)