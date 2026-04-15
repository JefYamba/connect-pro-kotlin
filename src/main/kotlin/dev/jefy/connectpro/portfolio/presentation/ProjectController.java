package dev.jefy.connectpro.portfolio.presentation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.portfolio.applicaion.command.ProjectCommand;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ProjectRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ProjectResponse;
import dev.jefy.connectpro.portfolio.applicaion.query.ProjectQuery;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId;
import dev.jefy.connectpro.shared.application.dtos.AppResponse;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
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
@RequestMapping("/project")
@Tag(name = "Projects Api")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectCommand command;
    private final ProjectQuery query;

    @GetMapping("/{projectId}")
    @Operation(summary = "Get project by ID")
    public ResponseEntity<AppResponse<ProjectResponse>> get(
            @PathVariable UUID projectId
    ) {
        return buildResponse("Project retrieved successfully", query.get(new ProjectId(projectId)));
    }

    @GetMapping("/portfolio/{portfolioId}")
    @Operation(summary = "Get all projects for a portfolio")
    public ResponseEntity<AppResponse<List<ProjectResponse>>> getAllForPortfolio(
            @PathVariable UUID portfolioId
    ) {
        return AppResponseBuilder.<List<ProjectResponse>>builder()
                .message("Projects retrieved successfully")
                .data(query.getAllForPortfolio(new PortfolioId(portfolioId)))
                .build();
    }
    
    @PostMapping()
    @Operation(summary = "Create a portfolio project")
    public ResponseEntity<AppResponse<ProjectResponse>> create(
            @RequestBody @Valid @NotNull ProjectRequest request
    ) {
        ProjectId projectId  = command.create(request);
        return buildResponse("Project created successfully", query.get(projectId));
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "Update a project")
    public ResponseEntity<AppResponse<ProjectResponse>> update(
            @PathVariable @NotNull UUID projectId,
            @RequestBody @NotNull @Valid ProjectRequest request
    ) {
        ProjectId id = command.update(ProjectId.of(projectId), request);
        return buildResponse("Project updated successfully", query.get(id));
    }

    @PostMapping("/{projectId}/images")
    @Operation(summary = "Add image")
    public ResponseEntity<AppResponse<ProjectResponse>> addImage(
            @PathVariable @NotNull UUID projectId,
            @RequestParam @NotNull MultipartFile image
    ) throws IOException {
        ProjectId id = command.addImage(ProjectId.of(projectId), image);
        return buildResponse("Image added", query.get(id));
    }

    @DeleteMapping("/{projectId}/images")
    @Operation(summary = "Remove image")
    public ResponseEntity<AppResponse<ProjectResponse>> removeImage(
            @PathVariable @NotNull UUID projectId,
            @RequestParam @NotNull ImageUrl imageUrl
    ) throws IOException {
        ProjectId id = command.removeImage(ProjectId.of(projectId), imageUrl);
        return buildResponse("Image removed", query.get(id));
    }

    private ResponseEntity<AppResponse<ProjectResponse>> buildResponse(String message, ProjectResponse data) {
        return AppResponseBuilder.<ProjectResponse>builder()
                .message(message)
                .data(data)
                .build();
    }
}
