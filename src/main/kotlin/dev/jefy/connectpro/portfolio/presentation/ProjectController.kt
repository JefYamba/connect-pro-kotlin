package dev.jefy.connectpro.portfolio.presentation

import dev.jefy.connectpro.portfolio.application.command.ProjectCommand
import dev.jefy.connectpro.portfolio.application.dtos.ProjectRequest
import dev.jefy.connectpro.portfolio.application.dtos.ProjectResponse
import dev.jefy.connectpro.portfolio.application.query.ProjectQuery
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/project")
@Tag(name = "Projects Api")
class ProjectController(
    private val command: ProjectCommand,
    private val query: ProjectQuery
) {

    @GetMapping("/{projectId}")
    @Operation(summary = "Get project by ID")
    fun get(@PathVariable projectId: UUID): ResponseEntity<AppResponse<ProjectResponse>> {
        return buildResponse("Project retrieved successfully", query.get(ProjectId.of(projectId)))
    }

    @GetMapping("/portfolio/{portfolioId}")
    @Operation(summary = "Get all projects for a portfolio")
    fun getAllForPortfolio(@PathVariable portfolioId: UUID): ResponseEntity<AppResponse<List<ProjectResponse>>> {
        return ResponseEntity.ok(
            AppResponse(
                message = "Projects retrieved successfully",
                data = query.getAllForPortfolio(PortfolioId(portfolioId)),
                status = HttpStatus.OK.value(),
                timestamp = Instant.now()
            )
        )
    }

    @PostMapping
    @Operation(summary = "Create a portfolio project")
    fun create(@RequestBody @Valid @NotNull request: ProjectRequest): ResponseEntity<AppResponse<ProjectResponse>> {
        val projectId = command.create(request)
        return buildResponse("Project created successfully", query.get(projectId))
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "Update a project")
    fun update(
        @PathVariable @NotNull projectId: UUID,
        @RequestBody @NotNull @Valid request: ProjectRequest
    ): ResponseEntity<AppResponse<ProjectResponse>> {
        val id = command.update(ProjectId.of(projectId), request)
        return buildResponse("Project updated successfully", query.get(id))
    }

    @PostMapping("/{projectId}/images")
    @Operation(summary = "Add image")
    @Throws(IOException::class)
    fun addImage(
        @PathVariable @NotNull projectId: UUID,
        @RequestParam @NotNull image: MultipartFile
    ): ResponseEntity<AppResponse<ProjectResponse>> {
        val id = command.addImage(ProjectId.of(projectId), image)
        return buildResponse("Image added", query.get(id))
    }

    @DeleteMapping("/{projectId}/images")
    @Operation(summary = "Remove image")
    @Throws(IOException::class)
    fun removeImage(
        @PathVariable @NotNull projectId: UUID,
        @RequestParam @NotNull imageUrl: ImageUrl
    ): ResponseEntity<AppResponse<ProjectResponse>> {
        val id = command.removeImage(ProjectId.of(projectId), imageUrl)
        return buildResponse("Image removed", query.get(id))
    }
    
    @DeleteMapping("/{projectId}")
    @Operation(summary = "Remove image")
    @Throws(IOException::class)
    fun delete(
        @PathVariable @NotNull projectId: UUID,
    ): ResponseEntity<AppResponse<ProjectResponse>> {
        command.delete(ProjectId.of(projectId))
        return buildResponse("Project deleted", null);
    }

    private fun buildResponse(message: String, data: ProjectResponse?): ResponseEntity<AppResponse<ProjectResponse>> {
        return ResponseEntity.ok(
            AppResponse(
                message = message,
                data = data,
                status = HttpStatus.OK.value(),
                timestamp = Instant.now()
            )
        )
    }
}