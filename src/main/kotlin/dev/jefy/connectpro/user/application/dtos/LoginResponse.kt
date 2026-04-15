package dev.jefy.connectpro.user.application.dtos

data class LoginResponse(val user: UserResponse, val token: String)