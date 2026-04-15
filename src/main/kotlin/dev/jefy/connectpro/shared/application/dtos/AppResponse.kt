package dev.jefy.connectpro.shared.application.dtos

import java.time.Instant

data class AppResponse<T>(
    val message: String,
    val data: T,
    val status: Int,
    val timestamp: Instant
)