package dev.jefy.connectpro.portfolio.presentation

import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.portfolio.application.command.PortfolioCommand
import dev.jefy.connectpro.portfolio.application.dtos.*
import dev.jefy.connectpro.portfolio.application.query.PortfolioQuery
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioType
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.util.UUID

@RestController
@RequestMapping("/portfolio")
@Tag(name = "Portfolio Api")
class PortfolioController(
    private val command: PortfolioCommand,
    private val query: PortfolioQuery
) {

    @GetMapping("/{id}")
    @Operation(summary = "Get portfolio details by id")
    fun getById(@PathVariable id: UUID): ResponseEntity<AppResponse<PortfolioResponse>> {
        val response = query.get(PortfolioId.of(id))
        return buildResponse("Portfolio details retrieved successfully", response)
    }

    @PostMapping
    @Operation(summary = "Create a portfolio for a user")
    fun create(@RequestBody @Valid request: PortfolioRequest): ResponseEntity<AppResponse<PortfolioResponse>> {
        val portfolioId = command.create(request)
        return buildResponse("Account created successfully", query.get(portfolioId))
    }

    @PatchMapping("/{portfolioId}/general-info")
    fun updateGeneralInfo(
        @PathVariable portfolioId: UUID,
        @RequestBody @Valid request: GeneralInfoRequest
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.updateGeneralInfo(PortfolioId.of(portfolioId), request)
        return buildResponse("General info updated successfully", query.get(id))
    }

    @PatchMapping("/{portfolioId}/professional-info")
    fun updateProfessionalInfo(
        @PathVariable portfolioId: UUID,
        @RequestBody @Valid request: ProfessionalInfoRequest
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.updateProfessional(PortfolioId.of(portfolioId), request)
        return buildResponse("Professional info updated successfully", query.get(id))
    }

    @PatchMapping("/{portfolioId}/contact-info")
    fun updateContactInfo(
        @PathVariable portfolioId: UUID,
        @RequestBody @Valid request: ContactInfoData
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.updateContactInfo(PortfolioId.of(portfolioId), request)
        return buildResponse("Contact info updated successfully", query.get(id))
    }

    @PatchMapping("/{portfolioId}/location-info")
    fun updateLocationInfo(
        @PathVariable portfolioId: UUID,
        @RequestBody @Valid request: LocationInfoData
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.updateLocationInfo(PortfolioId.of(portfolioId), request)
        return buildResponse("Location info updated successfully", query.get(id))
    }

    @PutMapping("/{portfolioId}/cover-image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Set portfolio cover image")
    fun setCoverImage(
        @PathVariable portfolioId: UUID,
        @RequestPart("image") image: MultipartFile
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.setCoverImage(PortfolioId.of(portfolioId), image)
        return buildResponse("Cover image updated successfully", query.get(id))
    }

    @DeleteMapping("/{portfolioId}/cover-image")
    fun deleteCoverImage(@PathVariable portfolioId: UUID): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.deleteCoverImage(PortfolioId.of(portfolioId))
        return buildResponse("Cover image deleted successfully", query.get(id))
    }

    @PostMapping("/{portfolioId}/social-links")
    fun addSocialLink(
        @PathVariable portfolioId: UUID,
        @RequestBody @Valid linkData: SocialLinkData
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.addSocialLink(PortfolioId.of(portfolioId), linkData)
        return buildResponse("Social link added successfully", query.get(id))
    }

    @DeleteMapping("/{portfolioId}/social-links/{socialLinkId}")
    fun deleteSocialLink(
        @PathVariable portfolioId: UUID,
        @PathVariable socialLinkId: UUID
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.deleteSocialLink(PortfolioId.of(portfolioId), SocialLinkId.of(socialLinkId))
        return buildResponse("Social link deleted successfully", query.get(id))
    }

    @PatchMapping("/{portfolioId}/status-activate")
    fun activatePortfolio(@PathVariable portfolioId: UUID): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.activate(PortfolioId.of(portfolioId))
        return buildResponse("Portfolio activated successfully", query.get(id))
    }

    @PatchMapping("/{portfolioId}/status-deactivate")
    fun deactivatePortfolio(@PathVariable portfolioId: UUID): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.deactivate(PortfolioId.of(portfolioId))
        return buildResponse("Portfolio deactivated successfully", query.get(id))
    }

    @PatchMapping("/{portfolioId}/status-bloc")
    fun blocPortfolio(@PathVariable portfolioId: UUID): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.bloc(PortfolioId.of(portfolioId))
        return buildResponse("Portfolio blocked successfully", query.get(id))
    }

    @PatchMapping("/{portfolioId}/type-change", consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun changeType(
        @PathVariable portfolioId: UUID,
        @RequestBody @Valid type: PortfolioType
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.changeType(PortfolioId.of(portfolioId), type)
        return buildResponse("Portfolio type changed successfully", query.get(id))
    }

    @PatchMapping("/{portfolioId}/badge/{badgeId}")
    fun changeBadge(
        @PathVariable portfolioId: UUID,
        @PathVariable badgeId: UUID
    ): ResponseEntity<AppResponse<PortfolioResponse>> {
        val id = command.setBadge(PortfolioId.of(portfolioId), BadgeId.of(badgeId))
        return buildResponse("Portfolio type changed successfully", query.get(id))
    }

    private fun buildResponse(message: String, response: PortfolioResponse): ResponseEntity<AppResponse<PortfolioResponse>> {
        return ResponseEntity.ok(
            AppResponse(
                message = message,
                data = response,
                status = HttpStatus.OK.value(),
                timestamp = Instant.now()
            )
        )
    }
}