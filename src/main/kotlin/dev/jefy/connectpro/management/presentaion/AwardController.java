package dev.jefy.connectpro.management.presentaion;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.appliacaion.command.AwardCommand;
import dev.jefy.connectpro.management.appliacaion.dtos.AwardRequest;
import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse;
import dev.jefy.connectpro.management.appliacaion.query.AwardQuery;
import dev.jefy.connectpro.management.domain.vo.AwardId;
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
@RequestMapping("/award")
@Tag(name = "Award Api")
@RequiredArgsConstructor
public class AwardController {
    private final AwardCommand command;
    private final AwardQuery query;

    @GetMapping("/{id}")
    @Operation(summary = "Get award by ID")
    public ResponseEntity<AppResponse<AwardResponse>> get(@PathVariable UUID id) {
        return buildResponse(
                "Award retrieved successfully", 
                query.get(AwardId.of(id))
        );
    }

    @GetMapping
    @Operation(summary = "Get all awards")
    public ResponseEntity<AppResponse<List<AwardResponse>>> getAll() {
        return AppResponseBuilder.<List<AwardResponse>>builder()
                .message("Awards retrieved successfully")
                .data(query.getAll())
                .build();
    }

    @PostMapping
    @Operation(summary = "Create award")
    public ResponseEntity<AppResponse<AwardResponse>> create(
            @RequestBody @Valid AwardRequest request
    ) {
        AwardId id = command.create(request);
        return buildResponse("Award created successfully", query.get(id));
    }

    @PutMapping("/{awardId}")
    @Operation(summary = "Update award")
    public ResponseEntity<AppResponse<AwardResponse>> update(
            @PathVariable UUID awardId,
            @RequestBody @Valid AwardRequest request
    ) {
        AwardId id = command.update(AwardId.of(awardId), request);
        return buildResponse("Award updated successfully", query.get(id));
    }

    @DeleteMapping("/{awardId}")
    @Operation(summary = "Delete award")
    public ResponseEntity<AppResponse<Void>> delete(
            @PathVariable UUID awardId
    ) {
        command.delete(AwardId.of(awardId));
        return AppResponseBuilder.<Void>builder()
                .message("Award deleted successfully")
                .build();
    }

    private ResponseEntity<AppResponse<AwardResponse>> buildResponse(
            String message,
            AwardResponse data
    ) {
        return AppResponseBuilder.<AwardResponse>builder()
                .message(message)
                .data(data)
                .build();
    }
}

