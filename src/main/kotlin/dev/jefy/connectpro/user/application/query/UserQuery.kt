package dev.jefy.connectpro.user.application.query

import dev.jefy.connectpro.user.application.dtos.UserResponse

interface UserQuery {
    fun getAuthenticatedUser(): UserResponse
}