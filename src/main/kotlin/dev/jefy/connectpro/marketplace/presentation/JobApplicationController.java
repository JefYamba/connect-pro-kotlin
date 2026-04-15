package dev.jefy.connectpro.marketplace.presentation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.marketplace.applicaion.command.JobApplicationCommand;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationRequest;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationResponseForEmployer;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobApplicationResponseForUser;
import dev.jefy.connectpro.marketplace.applicaion.query.JobApplicationQuery;
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.shared.application.dtos.AppResponse;
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder;
import dev.jefy.connectpro.user.domain.vo.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/job-applications")
@Tag(name = "Job Application Api")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationCommand command;
    private final JobApplicationQuery query;


    @GetMapping("/my")
    @Operation(summary = "Get all applications for current user")
    public ResponseEntity<AppResponse<List<JobApplicationResponseForUser>>> findForCurrentUser() {
        return AppResponseBuilder.<List<JobApplicationResponseForUser>>builder()
                .message("Applications retrieved successfully")
                .data(query.findForCurrentUser())
                .build();
    }

    @GetMapping("/job-post/{jobPostId}")
    @Operation(summary = "Get all applications by job post (employer)")
    public ResponseEntity<AppResponse<List<JobApplicationResponseForEmployer>>> findByJobPost(
            @PathVariable UUID jobPostId
    ) {
        return AppResponseBuilder.<List<JobApplicationResponseForEmployer>>builder()
                .message("Applications retrieved successfully")
                .data(query.findByJobPost(new JobPostId(jobPostId)))
                .build();
    }

    @GetMapping("/job-post/{jobPostId}/my")
    @Operation(summary = "Get current user's application for a job post")
    public ResponseEntity<AppResponse<JobApplicationResponseForUser>> getForUser(
            @PathVariable UUID jobPostId
    ) {
        return buildResponseForUser(
                "Application retrieved successfully",
                query.getForUser(new JobPostId(jobPostId))
        );
    }

    @GetMapping("/{jobPostId}/{applicantId}")
    @Operation(summary = "Get application by ID (employer)")
    public ResponseEntity<AppResponse<JobApplicationResponseForEmployer>> getForEmployer(
            @PathVariable @NotNull UUID jobPostId,
            @PathVariable @NotNull UUID applicantId
    ) {
        var applicationId = JobApplicationId.of(UserId.of(applicantId), JobPostId.of(jobPostId));
        return buildResponseForEmployer(
                "Application retrieved successfully",
                query.getForEmployer(applicationId)
        );
    }
    

    @PostMapping
    @Operation(summary = "Apply to a job")
    public ResponseEntity<AppResponse<JobApplicationResponseForUser>> apply(
            @RequestBody @Valid JobApplicationRequest request
    ) {
        JobApplicationId id = command.apply(request);
        return buildResponseForUser(
                "Application submitted successfully",
                query.getForUser(JobPostId.of(id.jobPostId()))
        );
    }

    @PatchMapping("/{jobPostId}/{applicantId}/accept")
    @Operation(summary = "Accept a job application")
    public ResponseEntity<AppResponse<JobApplicationResponseForEmployer>> accept(
            @PathVariable @NotNull UUID jobPostId,
            @PathVariable @NotNull UUID applicantId
    ) {
        var applicationId = JobApplicationId.of(UserId.of(applicantId), JobPostId.of(jobPostId));
        JobApplicationId id = command.accept(applicationId);
        
        return buildResponseForEmployer("Application accepted", query.getForEmployer(id));
    }

    @PatchMapping("/{jobPostId}/{applicantId}/reject")
    @Operation(summary = "Reject a job application")
    public ResponseEntity<AppResponse<JobApplicationResponseForEmployer>> reject(
            @PathVariable UUID jobPostId,
            @PathVariable UUID applicantId
    ) {
        var applicationId = JobApplicationId.of(UserId.of(applicantId), JobPostId.of(jobPostId));
        JobApplicationId id = command.reject(applicationId);
        
        return buildResponseForEmployer("Application rejected", query.getForEmployer(id));
    }

    private ResponseEntity<AppResponse<JobApplicationResponseForUser>> buildResponseForUser(
            String message,
            JobApplicationResponseForUser data
    ) {
        return AppResponseBuilder.<JobApplicationResponseForUser>builder()
                .message(message)
                .data(data)
                .build();
    }

    private ResponseEntity<AppResponse<JobApplicationResponseForEmployer>> buildResponseForEmployer(
            String message,
            JobApplicationResponseForEmployer data
    ) {
        return AppResponseBuilder.<JobApplicationResponseForEmployer>builder()
                .message(message)
                .data(data)
                .build();
    }
}
