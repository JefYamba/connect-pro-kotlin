package dev.jefy.connectpro.marketplace.presentation

import dev.jefy.connectpro.marketplace.application.command.JobApplicationCommand
import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationRequest
import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationResponseForEmployer
import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationResponseForUser
import dev.jefy.connectpro.marketplace.application.query.JobApplicationQuery
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.user.domain.vo.UserId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.UUID

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/job-applications")
@Tag(name = "Job Application Api")
class JobApplicationController(
    private val command: JobApplicationCommand,
    private val query: JobApplicationQuery
) {

    @GetMapping("/my")
    @Operation(summary = "Get all applications for current user")
    fun findForCurrentUser(): ResponseEntity<AppResponse<List<JobApplicationResponseForUser>>> = ResponseEntity.ok(
        AppResponse(
            message = "Applications retrieved successfully",
            data = query.findForCurrentUser(),
            status = HttpStatus.OK.value(),
            timestamp = Instant.now()
        )
    )
    
    @GetMapping("/job-post/{jobPostId}")
    @Operation(summary = "Get all applications by job post (employer)")
    fun findByJobPost(
        @PathVariable jobPostId: UUID
    ): ResponseEntity<AppResponse<List<JobApplicationResponseForEmployer>>> = ResponseEntity.ok(
        AppResponse(
            message = "Applications retrieved successfully",
            data = query.findByJobPost(JobPostId(jobPostId)),
            status = HttpStatus.OK.value(),
            timestamp = Instant.now()
        )
    )


    @GetMapping("/job-post/{jobPostId}/my")
    @Operation(summary = "Get current user's application for a job post")
    fun getForUser(
        @PathVariable jobPostId: UUID
    ): ResponseEntity<AppResponse<JobApplicationResponseForUser>> =
        buildResponseForUser(
            "Application retrieved successfully",
            query.getForUser(JobPostId(jobPostId))
        )

    @GetMapping("/{jobPostId}/{applicantId}")
    @Operation(summary = "Get application by ID (employer)")
    fun getForEmployer(
        @PathVariable @NotNull jobPostId: UUID,
        @PathVariable @NotNull applicantId: UUID
    ): ResponseEntity<AppResponse<JobApplicationResponseForEmployer>> {
        val applicationId = JobApplicationId(applicantId = applicantId, jobPostId = jobPostId)
        return buildResponseForEmployer(
            "Application retrieved successfully",
            query.getForEmployer(applicationId)
        )
    }

    @PostMapping
    @Operation(summary = "Apply to a job")
    fun apply(
        @RequestBody @Valid request: JobApplicationRequest
    ): ResponseEntity<AppResponse<JobApplicationResponseForUser>> {
        val id = command.apply(request)
        return buildResponseForUser(
            "Application submitted successfully",
            query.getForUser(JobPostId.of(id.jobPostId))
        )
    }

    @PatchMapping("/{jobPostId}/{applicantId}/accept")
    @Operation(summary = "Accept a job application")
    fun accept(
        @PathVariable @NotNull jobPostId: UUID,
        @PathVariable @NotNull applicantId: UUID
    ): ResponseEntity<AppResponse<JobApplicationResponseForEmployer>> {
        val applicationId = JobApplicationId(applicantId = applicantId, jobPostId = jobPostId)
        val id = command.accept(applicationId)
        return buildResponseForEmployer("Application accepted", query.getForEmployer(id))
    }

    @PatchMapping("/{jobPostId}/{applicantId}/reject")
    @Operation(summary = "Reject a job application")
    fun reject(
        @PathVariable jobPostId: UUID,
        @PathVariable applicantId: UUID
    ): ResponseEntity<AppResponse<JobApplicationResponseForEmployer>> {
        val applicationId = JobApplicationId(applicantId = applicantId, jobPostId = jobPostId)
        val id = command.reject(applicationId)
        return buildResponseForEmployer("Application rejected", query.getForEmployer(id))
    }

    private fun buildResponseForUser(
        message: String,
        data: JobApplicationResponseForUser
    ): ResponseEntity<AppResponse<JobApplicationResponseForUser>> = ResponseEntity.ok(
        AppResponse(
            message = message,
            data = data,
            status = HttpStatus.OK.value(),
            timestamp = Instant.now()
        )
    )

    private fun buildResponseForEmployer(
        message: String,
        data: JobApplicationResponseForEmployer
    ): ResponseEntity<AppResponse<JobApplicationResponseForEmployer>> = ResponseEntity.ok(
        AppResponse(
            message = message,
            data = data,
            status = HttpStatus.OK.value(),
            timestamp = Instant.now()
        )
        )
}