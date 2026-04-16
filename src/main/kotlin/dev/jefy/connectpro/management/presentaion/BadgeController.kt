package dev.jefy.connectpro.management.presentaion;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.appliacaion.command.BadgeCommand;
import dev.jefy.connectpro.management.appliacaion.dtos.BadgeRequest;
import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse;
import dev.jefy.connectpro.management.appliacaion.query.BadgeQuery;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.shared.application.dtos.AppResponse;
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/badge")
@Tag(name = "Badge Api")
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeCommand command;
    private final BadgeQuery query;


    @GetMapping("/{id}")
    @Operation(summary = "Get badge by ID")
    public ResponseEntity<AppResponse<BadgeResponse>> get(@PathVariable UUID id) {
        return buildResponse(
                "Badge retrieved successfully", 
                query.get(BadgeId.of(id))
        );
    }

    @GetMapping
    @Operation(summary = "Get all badges")
    public ResponseEntity<AppResponse<List<BadgeResponse>>> getAll() {
        return AppResponseBuilder.<List<BadgeResponse>>builder()
                .message("Badges retrieved successfully")
                .data(query.all)
                .build();
    }
    
    @PostMapping
    @Operation(summary = "Create badge")
    public ResponseEntity<AppResponse<BadgeResponse>> create(
            @RequestBody @Valid BadgeRequest request
    ) {
        BadgeId id = command.create(request);
        return buildResponse("Badge created successfully", query.get(id));
    }

    @PutMapping("/{badgeId}")
    @Operation(summary = "Update badge")
    public ResponseEntity<AppResponse<BadgeResponse>> update(
            @PathVariable UUID badgeId,
            @RequestBody @Valid BadgeRequest request
    ) {
        BadgeId id = command.update(BadgeId.of(badgeId), request);
        return buildResponse("Badge updated successfully", query.get(id));
    }

    @DeleteMapping("/{badgeId}")
    @Operation(summary = "Delete badge")
    public ResponseEntity<AppResponse<Void>> delete(
            @PathVariable UUID badgeId
    ) {
        command.delete(BadgeId.of(badgeId));
        return AppResponseBuilder.<Void>builder()
                .message("Badge deleted successfully")
                .build();
    }

    private ResponseEntity<AppResponse<BadgeResponse>> buildResponse(
            String message,
            BadgeResponse data
    ) {
        return AppResponseBuilder.<BadgeResponse>builder()
                .message(message)
                .data(data)
                .build();
    }
}
