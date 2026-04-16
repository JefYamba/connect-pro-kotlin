package dev.jefy.connectpro.engagement.presentation;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.engagement.applicaion.command.ReviewCommand;
import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewRequest;
import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewResponse;
import dev.jefy.connectpro.engagement.applicaion.query.ReviewQuery;
import dev.jefy.connectpro.shared.application.dtos.AppResponse;
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/services/{serviceId}/reviews")
@Tag(name = "Review Api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewCommand command;
    private final ReviewQuery query;

    @GetMapping("/me")
    @Operation(summary = "Get Service review for current user")
    public ResponseEntity<AppResponse<ReviewResponse>> findServiceReviewForCurrentUser(
            @PathVariable @NotNull UUID serviceId
    ) {
        ReviewResponse response = query.findServiceReviewForCurrentUser(ServiceId.of(serviceId));

        if (response == null) {
            return AppResponseBuilder.<ReviewResponse>builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("no review found")
                    .build();
        }

        return AppResponseBuilder.<ReviewResponse>builder()
                .message("review retrieved successfully")
                .data(response)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get Service reviews")
    public ResponseEntity<AppResponse<List<ReviewResponse>>> findByService(
            @PathVariable UUID serviceId
    ) {
        var response =  query.findByService(ServiceId.of(serviceId));
        return AppResponseBuilder.<List<ReviewResponse>>builder()
                .message("reviews retrieved successfully")
                .data(response)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create or update service review")
    public void createOrUpdate(
            @PathVariable @NotNull UUID serviceId,
            @RequestBody @NotNull @Valid ReviewRequest request
    ) {
        command.createOrUpdate(ServiceId.of(serviceId), request);
    }

    @DeleteMapping
    @Operation(summary = "Delete review")
    public void delete(@PathVariable @NotNull UUID serviceId) {
        command.delete(ServiceId.of(serviceId));
    }
}
