package dev.jefy.connectpro.portfolio.presentation

import dev.jefy.connectpro.portfolio.application.command.JobPostCommand
import dev.jefy.connectpro.portfolio.application.dtos.JobPostRequest
import dev.jefy.connectpro.portfolio.application.dtos.JobPostResponse
import dev.jefy.connectpro.portfolio.application.query.PortfolioQuery
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.recommandation.RecommandationClient
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/job-post")
@Tag(name = "Job Post Api")
class JobPostController(
    private val command: JobPostCommand,
    private val query: PortfolioQuery,
    private val recommandationClient: RecommandationClient
) {

    @PostMapping
    @Operation(summary = "Create a job post for a user")
    fun create(@RequestBody @Valid @NotNull request: JobPostRequest): ResponseEntity<AppResponse<JobPostResponse>> {
        val jobPostId = command.create(request)
        return buildResponse("Account created successfully", query.getJobPost(jobPostId))
    }

    @PutMapping("/{jobPostId}")
    @Operation(summary = "Update a job post")
    fun update(
        @PathVariable @NotNull jobPostId: UUID,
        @RequestBody @Valid request: JobPostRequest
    ): ResponseEntity<AppResponse<JobPostResponse>> {
        val id = command.update(JobPostId.of(jobPostId), request)
        return buildResponse("job post updated successfully", query.getJobPost(id))
    }

    @PatchMapping("/{jobPostId}/open")
    @Operation(summary = "Update a job post")
    fun openJobPost(@PathVariable @NotNull jobPostId: UUID): ResponseEntity<AppResponse<JobPostResponse>> {
        val id = command.open(JobPostId.of(jobPostId))
        return buildResponse("Job post opened successfully", query.getJobPost(id))
    }

    @PatchMapping("/{jobPostId}/close")
    @Operation(summary = "Update a job post")
    fun closeJobPost(@PathVariable @NotNull jobPostId: UUID): ResponseEntity<AppResponse<JobPostResponse>> {
        val id = command.close(JobPostId.of(jobPostId))
        return buildResponse("Job post closed successfully", query.getJobPost(id))
    }

    @GetMapping("/{jobPostId}")
    @Operation(summary = "Get job post details")
    fun getJobPost(@PathVariable @NotNull jobPostId: UUID): ResponseEntity<AppResponse<JobPostResponse>> {
        val jobPost = query.getJobPost(JobPostId.of(jobPostId))
        recommandationClient.trackEvent(EventType.VIEW, jobPostId, TargetType.JOB_POST)
        return buildResponse("Job post retrieved successfully", jobPost)
    }

    private fun buildResponse(message: String, data: JobPostResponse): ResponseEntity<AppResponse<JobPostResponse>> {
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