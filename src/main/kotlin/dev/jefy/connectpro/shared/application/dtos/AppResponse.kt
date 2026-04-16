package dev.jefy.connectpro.shared.application.dtos

import org.springframework.http.HttpStatus
import java.time.Instant

data class AppResponse<T>(
    val message: String,
    val data: T?,
    val status: Int = HttpStatus.OK.value(),
    val timestamp: Instant = Instant.now()
)