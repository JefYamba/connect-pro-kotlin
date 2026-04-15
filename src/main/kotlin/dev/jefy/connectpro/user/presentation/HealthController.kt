package dev.jefy.connectpro.user.presentation

import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder
import dev.jefy.connectpro.user.application.dtos.UserResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
/**
 * @author  Jôph Yamba
 */
@RestController
@RequestMapping("/health")
@Tag(name = "Health Api")
class HealthController {

    @GetMapping
    fun getHealth(): ResponseEntity<AppResponse<UserResponse>> {
        return AppResponseBuilder.builder<UserResponse>()
            .message("Api is up and running")
            .status(HttpStatus.OK)
            .build()
    }
}