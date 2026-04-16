package dev.jefy.connectpro.portfolio.presentation

import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.portfolio.application.command.ServiceCommand
import dev.jefy.connectpro.portfolio.application.dtos.FAQRequest
import dev.jefy.connectpro.portfolio.application.dtos.ServiceRequest
import dev.jefy.connectpro.portfolio.application.dtos.ServiceResponse
import dev.jefy.connectpro.portfolio.application.query.PortfolioQuery
import dev.jefy.connectpro.portfolio.domain.vo.FAQId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.recommandation.RecommandationClient
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
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
@RequestMapping("/service")
@Tag(name = "Service Api")
class ServiceController(
    private val command: ServiceCommand,
    private val query: PortfolioQuery,
    private val recommandationClient: RecommandationClient
) {

    @PostMapping
    @Operation(summary = "Create a service for a user")
    fun create(@RequestBody @Valid @NotNull request: ServiceRequest): ResponseEntity<AppResponse<ServiceResponse>> {
        val serviceId = command.create(request)
        return buildResponse("Service created successfully", query.getService(serviceId))
    }

    @PutMapping("/{serviceId}")
    @Operation(summary = "Update a service")
    fun update(
        @PathVariable @NotNull serviceId: UUID,
        @RequestBody @NotNull @Valid request: ServiceRequest
    ): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.update(ServiceId.of(serviceId), request)
        return buildResponse("Service updated successfully", query.getService(id))
    }

    @PostMapping("/{serviceId}/cover-image")
    @Operation(summary = "Set cover image")
    @Throws(IOException::class)
    fun setCoverImage(
        @PathVariable @NotNull serviceId: UUID,
        @RequestParam @NotNull image: MultipartFile
    ): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.setCoverImage(ServiceId.of(serviceId), image)
        return buildResponse("Cover image updated", query.getService(id))
    }

    @DeleteMapping("/{serviceId}/cover-image")
    @Operation(summary = "Delete cover image")
    @Throws(IOException::class)
    fun deleteCoverImage(@PathVariable @NotNull serviceId: UUID): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.deleteCoverImage(ServiceId.of(serviceId))
        return buildResponse("Cover image removed", query.getService(id))
    }

    @PostMapping("/{serviceId}/images")
    @Operation(summary = "Add image")
    @Throws(IOException::class)
    fun addImage(
        @PathVariable @NotNull serviceId: UUID,
        @RequestParam @NotNull image: MultipartFile
    ): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.addImage(ServiceId.of(serviceId), image)
        return buildResponse("Image added", query.getService(id))
    }

    @DeleteMapping("/{serviceId}/images")
    @Operation(summary = "Remove image")
    @Throws(IOException::class)
    fun removeImage(
        @PathVariable @NotNull serviceId: UUID,
        @RequestParam @NotNull imageUrl: ImageUrl
    ): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.removeImage(ServiceId.of(serviceId), imageUrl)
        return buildResponse("Image removed", query.getService(id))
    }

    @PostMapping("/{serviceId}/faqs")
    @Operation(summary = "Add FAQ")
    fun addFaq(
        @PathVariable @NotNull serviceId: UUID,
        @RequestBody @NotNull @Valid request: FAQRequest
    ): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.addFaq(ServiceId.of(serviceId), request)
        return buildResponse("FAQ added", query.getService(id))
    }

    @DeleteMapping("/{serviceId}/faqs/{faqId}")
    @Operation(summary = "Remove FAQ")
    fun removeFaq(
        @PathVariable @NotNull serviceId: UUID,
        @PathVariable @NotNull faqId: UUID
    ): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.removeFaq(ServiceId.of(serviceId), FAQId.of(faqId))
        return buildResponse("FAQ removed", query.getService(id))
    }

    @PostMapping("/{serviceId}/award/{awardId}")
    @Operation(summary = "Set award")
    fun setAward(
        @PathVariable @NotNull serviceId: UUID,
        @PathVariable @NotNull awardId: UUID
    ): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.setAward(ServiceId.of(serviceId), AwardId.of(awardId))
        return buildResponse("Award set", query.getService(id))
    }

    @DeleteMapping("/{serviceId}/award")
    @Operation(summary = "Remove award")
    fun removeAward(@PathVariable @NotNull serviceId: UUID): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.removeAward(ServiceId.of(serviceId))
        return buildResponse("Award removed", query.getService(id))
    }

    @PostMapping("/{serviceId}/activate")
    @Operation(summary = "Activate service")
    fun activate(@PathVariable @NotNull serviceId: UUID): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.activateService(ServiceId.of(serviceId))
        return buildResponse("Service activated", query.getService(id))
    }

    @PostMapping("/{serviceId}/deactivate")
    @Operation(summary = "Deactivate service")
    fun deactivate(@PathVariable @NotNull serviceId: UUID): ResponseEntity<AppResponse<ServiceResponse>> {
        val id = command.deactivateService(ServiceId.of(serviceId))
        return buildResponse("Service deactivated", query.getService(id))
    }

    @GetMapping("/{serviceId}")
    @Operation(summary = "Get service details")
    fun getService(
        @PathVariable @NotNull serviceId: UUID
    ): ResponseEntity<AppResponse<ServiceResponse>> {
        val service = query.getService(ServiceId.of(serviceId))
        recommandationClient.trackEvent(EventType.VIEW, serviceId, TargetType.SERVICE)
        return buildResponse("Service retrieved successfully", service)
    }

    private fun buildResponse(message: String, data: ServiceResponse): ResponseEntity<AppResponse<ServiceResponse>> {
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