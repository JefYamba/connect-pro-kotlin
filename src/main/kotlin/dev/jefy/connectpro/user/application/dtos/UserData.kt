package dev.jefy.connectpro.user.application.dtos

import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import dev.jefy.connectpro.user.domain.model.AuthUser
import dev.jefy.connectpro.user.domain.model.User
import java.util.*

data class UserData(
    val id: UUID,
    val email: String,
    val name: String,
    val image: String? = null
)

fun User.toUserData(resolver: ImageUrlResolver): UserData = UserData(
    id = this.id.value,
    email = this.email.value,
    name = this.name,
    image = resolver.resolve(this.image)
)

fun AuthUser.toUserData(): UserData = UserData(
    id = this.id,
    email = this.email,
    name = this.fullname,
    image = this.image
)