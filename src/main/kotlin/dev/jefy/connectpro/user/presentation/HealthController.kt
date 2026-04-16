package dev.jefy.connectpro.user.presentation

import dev.jefy.connectpro.shared.application.dtos.AppResponse
import io.swagger.v3.oas.annotations.tags.Tag
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
    fun getHealth(): ResponseEntity<AppResponse<Unit>> {
        return ResponseEntity.ok(
            AppResponse(
                message = "Health check passed",
                data = Unit
            )
        );
    }
}