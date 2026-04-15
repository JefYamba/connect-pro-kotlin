package dev.jefy.connectpro.portfolio.presentation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.portfolio.applicaion.command.ServiceCommand;
import dev.jefy.connectpro.portfolio.applicaion.dtos.FAQRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ServiceRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ServiceResponse;
import dev.jefy.connectpro.portfolio.applicaion.query.PortfolioQuery;
import dev.jefy.connectpro.portfolio.domain.vo.FAQId;
import dev.jefy.connectpro.recommandation.RecommandationClient;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
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
@RequestMapping("/service")
@Tag(name = "Service Api")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceCommand command;
    private final PortfolioQuery query;
    private final RecommandationClient recommandationClient;

    @PostMapping()
    @Operation(summary = "Create a service for a user")
    public ResponseEntity<AppResponse<ServiceResponse>> create(
            @RequestBody @Valid @NotNull ServiceRequest request
    ) {
        ServiceId serviceId  = command.create(request);
        return buildResponse("Service created successfully", query.getService(serviceId));
    }

    @PutMapping("/{serviceId}")
    @Operation(summary = "Update a service")
    public ResponseEntity<AppResponse<ServiceResponse>> update(
            @PathVariable @NotNull UUID serviceId,
            @RequestBody @NotNull @Valid ServiceRequest request
    ) {
        ServiceId id = command.update(ServiceId.of(serviceId), request);
        return buildResponse("Service updated successfully", query.getService(id));
    }

    @PostMapping("/{serviceId}/cover-image")
    @Operation(summary = "Set cover image")
    public ResponseEntity<AppResponse<ServiceResponse>> setCoverImage(
            @PathVariable @NotNull UUID serviceId,
            @RequestParam @NotNull MultipartFile image
    ) throws IOException {
        ServiceId id = command.setCoverImage(ServiceId.of(serviceId), image);
        return buildResponse("Cover image updated", query.getService(id));
    }

    @DeleteMapping("/{serviceId}/cover-image")
    @Operation(summary = "Delete cover image")
    public ResponseEntity<AppResponse<ServiceResponse>> deleteCoverImage(
            @PathVariable @NotNull UUID serviceId
    ) throws IOException {
        ServiceId id = command.deleteCoverImage(ServiceId.of(serviceId));
        return buildResponse("Cover image removed", query.getService(id));
    }

    @PostMapping("/{serviceId}/images")
    @Operation(summary = "Add image")
    public ResponseEntity<AppResponse<ServiceResponse>> addImage(
            @PathVariable @NotNull UUID serviceId,
            @RequestParam @NotNull MultipartFile image
    ) throws IOException {
        ServiceId id = command.addImage(ServiceId.of(serviceId), image);
        return buildResponse("Image added", query.getService(id));
    }

    @DeleteMapping("/{serviceId}/images")
    @Operation(summary = "Remove image")
    public ResponseEntity<AppResponse<ServiceResponse>> removeImage(
            @PathVariable @NotNull UUID serviceId,
            @RequestParam @NotNull ImageUrl imageUrl
    ) throws IOException {
        ServiceId id = command.removeImage(ServiceId.of(serviceId), imageUrl);
        return buildResponse("Image removed", query.getService(id));
    }

    @PostMapping("/{serviceId}/faqs")
    @Operation(summary = "Add FAQ")
    public ResponseEntity<AppResponse<ServiceResponse>> addFaq(
            @PathVariable @NotNull UUID serviceId,
            @RequestBody @NotNull @Valid FAQRequest request
    ) {
        ServiceId id = command.addFaq(ServiceId.of(serviceId), request);
        return buildResponse("FAQ added", query.getService(id));
    }

    @DeleteMapping("/{serviceId}/faqs/{faqId}")
    @Operation(summary = "Remove FAQ")
    public ResponseEntity<AppResponse<ServiceResponse>> removeFaq(
            @PathVariable @NotNull UUID serviceId,
            @PathVariable @NotNull UUID faqId
    ) {
        ServiceId id = command.removeFaq(ServiceId.of(serviceId), FAQId.of(faqId));
        return buildResponse("FAQ removed", query.getService(id));
    }

    @PostMapping("/{serviceId}/award/{awardId}")
    @Operation(summary = "Set award")
    public ResponseEntity<AppResponse<ServiceResponse>> setAward(
            @PathVariable @NotNull UUID serviceId,
            @PathVariable @NotNull UUID awardId
    ) {
        ServiceId id = command.setAward(ServiceId.of(serviceId), AwardId.of(awardId));
        return buildResponse("Award set", query.getService(id));
    }

    @DeleteMapping("/{serviceId}/award")
    @Operation(summary = "Remove award")
    public ResponseEntity<AppResponse<ServiceResponse>> removeAward(
            @PathVariable @NotNull UUID serviceId
    ) {
        ServiceId id = command.removeAward(ServiceId.of(serviceId));
        return buildResponse("Award removed", query.getService(id));
    }

    @PostMapping("/{serviceId}/activate")
    @Operation(summary = "Activate service")
    public ResponseEntity<AppResponse<ServiceResponse>> activate(
            @PathVariable @NotNull UUID serviceId
    ) {
        ServiceId id = command.activateService(ServiceId.of(serviceId));
        return buildResponse("Service activated", query.getService(id));
    }

    @PostMapping("/{serviceId}/deactivate")
    @Operation(summary = "Deactivate service")
    public ResponseEntity<AppResponse<ServiceResponse>> deactivate(
            @PathVariable @NotNull UUID serviceId
    ) {
        ServiceId id = command.deactivateService(ServiceId.of(serviceId));
        return buildResponse("Service deactivated", query.getService(id));
    }

    @GetMapping("/{serviceId}")
    @Operation(summary = "Get service details")
    public ResponseEntity<AppResponse<ServiceResponse>> getService(
            @PathVariable @NotNull UUID serviceId
    ) {
        ServiceResponse service = query.getService(ServiceId.of(serviceId));
        recommandationClient.trackEvent(EventType.VIEW, serviceId, TargetType.SERVICE);
        return buildResponse("Service retrieved successfully", service);
    }

    // 🔥 Helper pour éviter la duplication
    private ResponseEntity<AppResponse<ServiceResponse>> buildResponse(String message, ServiceResponse data) {
        return AppResponseBuilder.<ServiceResponse>builder()
                .message(message)
                .data(data)
                .build();
    }
}