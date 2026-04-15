package dev.jefy.connectpro.user

import dev.jefy.connectpro.user.application.dtos.UserData
import dev.jefy.connectpro.user.domain.vo.UserId
/**
 * @author  Jôph Yamba
 */
interface UserClient {
    fun getData(userId: UserId): UserData
    fun getCurrentUser(): UserData
}