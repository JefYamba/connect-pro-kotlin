package dev.jefy.connectpro.engagement.presentation

import dev.jefy.connectpro.engagement.application.command.ReviewCommand
import dev.jefy.connectpro.engagement.application.dtos.ReviewRequest
import dev.jefy.connectpro.engagement.application.dtos.ReviewResponse
import dev.jefy.connectpro.engagement.application.query.ReviewQuery
import dev.jefy.connectpro.engagement.presentation.ReviewOperationResult.CREATED
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/services/{serviceId}/reviews")
@Tag(name = "Review Api")
class ReviewController(private val command: ReviewCommand, private val query: ReviewQuery) {

    @GetMapping("/me")
    @Operation(summary = "Get Service review for current user")
    fun findServiceReviewForCurrentUser(@PathVariable serviceId: UUID): ResponseEntity<AppResponse<ReviewResponse>> {
        
        val response = query.findServiceReviewForCurrentUser(ServiceId.of(serviceId))

        return ResponseEntity.ok(
            AppResponse(
                message = "review retrieved successfully",
                data = response,
                status = HttpStatus.OK.value(),
                timestamp = Instant.now()
            )
        )
    }

    @GetMapping
    @Operation(summary = "Get Service reviews")
    fun findByService(@PathVariable serviceId: UUID): ResponseEntity<AppResponse<List<ReviewResponse>>> {

        val response = query.findByService(ServiceId.of(serviceId))

        return ResponseEntity.ok(
            AppResponse(
                message = "reviews retrieved successfully",
                data = response,
                status = HttpStatus.OK.value(),
                timestamp = Instant.now()
            )
        )
    }

    @PostMapping
    @Operation(summary = "Create or update service review")
    fun createOrUpdate(
        @PathVariable serviceId: UUID, @RequestBody @Valid request: ReviewRequest
    ): ResponseEntity<AppResponse<Unit>> {
        val result = command.createOrUpdate(ServiceId(serviceId), request)
        val status = if (result == CREATED) HttpStatus.CREATED else HttpStatus.OK
        val message = if (result == CREATED) "Review created successfully" else "Review updated successfully"
        return ResponseEntity.status(status).body(
            AppResponse(
                message = message,
                data = null,
                status = status.value(),
                timestamp = Instant.now()
            )
        )
    }

    @DeleteMapping
    @Operation(summary = "Delete review")
    fun delete(@PathVariable serviceId: UUID): ResponseEntity<AppResponse<Unit>> {
        command.delete(ServiceId(serviceId))
        return ResponseEntity.ok(
            AppResponse(
                message = "reviews deleted successfully",
                data = null,
                status = HttpStatus.OK.value(),
                timestamp = Instant.now()
            )
        )
    }
}

enum class ReviewOperationResult {
    CREATED,
    UPDATED
}