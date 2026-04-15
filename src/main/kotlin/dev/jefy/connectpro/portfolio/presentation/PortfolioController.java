package dev.jefy.connectpro.portfolio.presentation;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.portfolio.applicaion.command.PortfolioCommand;
import dev.jefy.connectpro.portfolio.applicaion.dtos.*;
import dev.jefy.connectpro.portfolio.applicaion.query.PortfolioQuery;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType;
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId;
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
@RequestMapping("/portfolio")
@Tag(name = "Portfolio Api")
@RequiredArgsConstructor
public class PortfolioController {
    
    private final PortfolioCommand command;
    private final PortfolioQuery query;
    
    
    @GetMapping("/{id}")
    @Operation(summary = "Get portfolio details by id")
    public ResponseEntity<AppResponse<PortfolioResponse>> getById(
            @PathVariable @NotNull UUID id
    ) {
        PortfolioResponse response = query.get(PortfolioId.of(id));
        return AppResponseBuilder.<PortfolioResponse>builder()
                .message("Portfolio details retrieved successfully")
                .data(response)
                .build();
    }

    @PostMapping()
    @Operation(summary = "Create a portfolio for a user")
    public ResponseEntity<AppResponse<PortfolioResponse>> create(
            @RequestBody
            @Valid
            @NotNull
            PortfolioRequest request
    ) {
        PortfolioId portfolioId  = command.create(request);
        return buildReponse("Account created successfully", query.get(portfolioId));
    }
    

    @PatchMapping("/{portfolioId}/general-info")
    public ResponseEntity<AppResponse<PortfolioResponse>> updateGeneralInfo(
            @PathVariable @NotNull UUID portfolioId,
            @RequestBody @Valid @NotNull GeneralInfoRequest request
    ) {
        PortfolioId id = command.updateGeneralInfo(PortfolioId.of(portfolioId), request);
        return buildReponse("General info updated successfully", query.get(id));
    }

    @PatchMapping("/{portfolioId}/professional-info")
    public ResponseEntity<AppResponse<PortfolioResponse>> updateProfessionalInfo(
            @PathVariable @NotNull UUID portfolioId,
            @RequestBody @Valid @NotNull ProfessionalInfoRequest request
    ) {
        PortfolioId id = command.updateProfessional(PortfolioId.of(portfolioId), request);
        return buildReponse("Professional info updated successfully", query.get(id));
    }

    @PatchMapping("/{portfolioId}/contact-info")
    public ResponseEntity<AppResponse<PortfolioResponse>> updateContactInfo(
            @PathVariable @NotNull UUID portfolioId,
            @RequestBody @Valid @NotNull ContactInfoData request
    ) {
        PortfolioId id = command.updateContactInfo(PortfolioId.of(portfolioId), request);
        return buildReponse("Contact info updated successfully", query.get(id));
    }

    @PatchMapping("/{portfolioId}/location-info")
    public ResponseEntity<AppResponse<PortfolioResponse>> updateLocationInfo(
            @PathVariable @NotNull UUID portfolioId,
            @RequestBody @Valid @NotNull LocationInfoData request
    ) {
        PortfolioId id = command.updateLocationInfo(PortfolioId.of(portfolioId), request);
        return buildReponse("Location info updated successfully", query.get(id));
    }
    
    @PutMapping(value = "/{portfolioId}/cover-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Set portfolio cover image")
    public ResponseEntity<AppResponse<PortfolioResponse>> setCoverImage(
            @PathVariable @NotNull UUID portfolioId,
            @RequestPart("image") @NotNull MultipartFile image
    ) throws IOException {
        PortfolioId id = command.setCoverImage(PortfolioId.of(portfolioId), image);
        return buildReponse("Cover image updated successfully", query.get(id));
    }

    @DeleteMapping("/{portfolioId}/cover-image")
    public ResponseEntity<AppResponse<PortfolioResponse>> deleteCoverImage(
            @PathVariable @NotNull UUID portfolioId
    ) throws IOException {
        PortfolioId id = command.deleteCoverImage(PortfolioId.of(portfolioId));
        return buildReponse("Cover image deleted successfully", query.get(id));
    }

    @PostMapping("/{portfolioId}/social-links")
    public ResponseEntity<AppResponse<PortfolioResponse>> addSocialLink(
            @PathVariable @NotNull UUID portfolioId,
            @RequestBody @Valid @NotNull SocialLinkData linkData
    ) {
        PortfolioId id = command.addSocialLink(PortfolioId.of(portfolioId), linkData);
        return buildReponse("Social link added successfully", query.get(id));
    }

    @DeleteMapping("/{portfolioId}/social-links/{socialLinkId}")
    public ResponseEntity<AppResponse<PortfolioResponse>> deleteSocialLink(
            @PathVariable @NotNull UUID portfolioId,
            @PathVariable String socialLinkId
    ) {
        PortfolioId id = command.deleteSocialLink(PortfolioId.of(portfolioId), SocialLinkId.of(socialLinkId));
        return buildReponse("Social link deleted successfully", query.get(id));
    }

    @PatchMapping("/{portfolioId}/status-activate")
    public ResponseEntity<AppResponse<PortfolioResponse>> activatePortfolio(
            @PathVariable @NotNull UUID portfolioId
    ) {
        PortfolioId id = command.activate(PortfolioId.of(portfolioId));
        return buildReponse("Portfolio activated successfully", query.get(id));
    }
    
    @PatchMapping("/{portfolioId}/status-deactivate")
    public ResponseEntity<AppResponse<PortfolioResponse>> deactivatePortfolio(
            @PathVariable @NotNull UUID portfolioId
    ) {
        PortfolioId id = command.deactivate(PortfolioId.of(portfolioId));
        return buildReponse("Portfolio deactivated successfully", query.get(id));
    }
    
    @PatchMapping("/{portfolioId}/status-bloc")
    public ResponseEntity<AppResponse<PortfolioResponse>> blocPortfolio(
            @PathVariable @NotNull UUID portfolioId
    ) {
        PortfolioId id = command.bloc(PortfolioId.of(portfolioId));
        return buildReponse("Portfolio blocked successfully", query.get(id));
    }
    @PatchMapping(value = "/{portfolioId}/type-change", consumes =  MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<AppResponse<PortfolioResponse>> changeType(
            @PathVariable @NotNull UUID portfolioId,
            @RequestBody @Valid @NotNull PortfolioType type
    ) {
        PortfolioId id = command.changeType(PortfolioId.of(portfolioId), type);
        return buildReponse("Portfolio type changed successfully", query.get(id));
    }
    @PatchMapping(value = "/{portfolioId}/badge/{badgeId}")
    public ResponseEntity<AppResponse<PortfolioResponse>> changeType(
            @PathVariable @NotNull UUID portfolioId,
            @PathVariable @NotNull UUID badgeId
    ) {
        PortfolioId id = command.setBadge(PortfolioId.of(portfolioId), BadgeId.of(badgeId));
        return buildReponse("Portfolio type changed successfully", query.get(id));
    }

    private static ResponseEntity<AppResponse<PortfolioResponse>> buildReponse(String message, PortfolioResponse response) {
        return AppResponseBuilder.<PortfolioResponse>builder()
                .message(message)
                .data(response)
                .build();
    }
}


