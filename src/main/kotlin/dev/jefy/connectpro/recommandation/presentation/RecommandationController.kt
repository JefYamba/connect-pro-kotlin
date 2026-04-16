package dev.jefy.connectpro.recommandation.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jefy.connectpro.recommandation.RecommandationClient;
import dev.jefy.connectpro.recommandation.application.dtos.EventTrackingRequest;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/recommandation")
@RequiredArgsConstructor
public class RecommandationController {

    private final RecommandationClient recommandationClient;

    @PostMapping("/track")
    public ResponseEntity<Void> track(@Valid @RequestBody EventTrackingRequest request) {
        // Only allow front-end tracking for VIEW and CLICK to avoid cheating on scores (LIKE/REVIEW are handled server-side)
        if (request.eventType() == EventType.VIEW || request.eventType() == EventType.CLICK) {
            recommandationClient.trackEvent(request.eventType(), request.targetId(), request.targetType());
        }
        return ResponseEntity.ok().build();
    }
}
