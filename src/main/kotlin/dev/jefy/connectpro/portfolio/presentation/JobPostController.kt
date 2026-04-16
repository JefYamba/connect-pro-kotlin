package dev.jefy.connectpro.portfolio.presentation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import dev.jefy.connectpro.portfolio.applicaion.command.JobPostCommand;
import dev.jefy.connectpro.portfolio.applicaion.dtos.JobPostRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.JobPostResponse;
import dev.jefy.connectpro.portfolio.applicaion.query.PortfolioQuery;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.recommandation.RecommandationClient;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
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
@RequestMapping("/job-post")
@Tag(name = "Job Post Api")
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostCommand command;
    private final PortfolioQuery query;
    private final RecommandationClient recommandationClient;

    @PostMapping()
    @Operation(summary = "Create a job post for a user")
    public ResponseEntity<AppResponse<JobPostResponse>> create(
            @RequestBody @Valid @NotNull JobPostRequest request
    ) {
        JobPostId jobPostId  = command.create(request);
        return buildResponse("Account created successfully", query.getJopPost(jobPostId));
    }

    @PutMapping("/{jobPostId}")
    @Operation(summary = "Update a job post")
    public ResponseEntity<AppResponse<JobPostResponse>> update(
            @PathVariable @NotNull UUID jobPostId,
            @RequestBody @Valid JobPostRequest request
    ) {
        JobPostId id = command.update(JobPostId.of(jobPostId), request);
        return buildResponse("job post updated successfully", query.getJopPost(id));
    }
    
    @PatchMapping("/{jobPostId}/open")
    @Operation(summary = "Update a job post")
    public ResponseEntity<AppResponse<JobPostResponse>> openJobPost(
            @PathVariable @NotNull UUID jobPostId
    ) {
        JobPostId id = command.open(JobPostId.of(jobPostId));
        return buildResponse("Job post opened successfully", query.getJopPost(id));
    }

    @PatchMapping("/{jobPostId}/close")
    @Operation(summary = "Update a job post")
    public ResponseEntity<AppResponse<JobPostResponse>> closeJobPost(
            @PathVariable @NotNull UUID jobPostId
    ) {
        JobPostId id = command.close(JobPostId.of(jobPostId));
        return buildResponse("Job post closed successfully", query.getJopPost(id));
    }

    @GetMapping("/{jobPostId}")
    @Operation(summary = "Get job post details")
    public ResponseEntity<AppResponse<JobPostResponse>> getJobPost(
            @PathVariable @NotNull UUID jobPostId
    ) {
        JobPostResponse jobPost = query.getJopPost(JobPostId.of(jobPostId));
        recommandationClient.trackEvent(EventType.VIEW, jobPostId, TargetType.JOB_POST);
        return buildResponse("Job post retrieved successfully", jobPost);
    }

    private ResponseEntity<AppResponse<JobPostResponse>> buildResponse(String message, JobPostResponse data) {
        return AppResponseBuilder
                .<JobPostResponse>builder()
                .message(message)
                .data(data)
                .build();
    }
}
