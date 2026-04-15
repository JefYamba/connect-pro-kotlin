package dev.jefy.connectpro.user.presentation;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jefy.connectpro.shared.application.dtos.AppResponse;
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder;
import dev.jefy.connectpro.user.applicaion.dtos.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/health")
@Tag(name = "Health Api")
@RequiredArgsConstructor
public class HealthController {
    
    @GetMapping
    public ResponseEntity<AppResponse<UserResponse>> getHealth() {
        return AppResponseBuilder.<UserResponse>builder()
                .message("Api is up and running")
                .status(HttpStatus.OK)
                .build();
    }
}
