package dev.jefy.connectpro.user

import dev.jefy.connectpro.user.application.dtos.UserData
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.UserId
/**
 * @author  Jôph Yamba
 */
interface UserClient {
    fun getById(userId: UserId): UserData
    fun getByEmail(email: Email): UserData
    fun getCurrentUser(): UserData
}