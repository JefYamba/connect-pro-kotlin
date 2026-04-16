package dev.jefy.connectpro.recommandation.presentation

import dev.jefy.connectpro.recommandation.RecommandationClient
import dev.jefy.connectpro.recommandation.application.dtos.EventTrackingRequest
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/recommandation")
class RecommandationController(
    private val recommandationClient: RecommandationClient
) {

    @PostMapping("/track")
    fun track(@Valid @RequestBody request: EventTrackingRequest): ResponseEntity<Void> {
        // Only allow front-end tracking for VIEW and CLICK to avoid cheating on scores (LIKE/REVIEW are handled server-side)
        if (request.eventType == EventType.VIEW || request.eventType == EventType.CLICK) {
            recommandationClient.trackEvent(request.eventType, request.targetId, request.targetType)
        }
        return ResponseEntity.ok().build()
    }
}