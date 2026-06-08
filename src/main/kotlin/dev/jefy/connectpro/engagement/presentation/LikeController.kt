package dev.jefy.connectpro.engagement.presentation


import dev.jefy.connectpro.engagement.application.command.LikeCommand
import dev.jefy.connectpro.engagement.application.dtos.ReviewResponse
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/service/{serviceId}/like")
@Tag(name = "Like Api")
class LikeController(private val command: LikeCommand) {

    @PostMapping
    fun like(@PathVariable serviceId: UUID): ResponseEntity<AppResponse<Map<String,Boolean>>> {
        val hasLiked = command.like(ServiceId(serviceId))
        return ResponseEntity.ok(
            AppResponse(
                message = if (hasLiked) "liked successfully" else "disliked successfully",
                data = mapOf("hasLiked" to hasLiked),
                status = HttpStatus.OK.value(),
                timestamp = Instant.now()
            )
        )
    }
}

