package dev.jefy.connectpro.management.presentaion

import dev.jefy.connectpro.management.appliacaion.command.BadgeCommand
import dev.jefy.connectpro.management.appliacaion.dtos.BadgeRequest
import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse
import dev.jefy.connectpro.management.appliacaion.query.BadgeQuery
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/badge")
@Tag(name = "Badge Api")
class BadgeController(
    private val command: BadgeCommand,
    private val query: BadgeQuery
) {

    @GetMapping("/{id}")
    @Operation(summary = "Get badge by ID")
    fun get(@PathVariable id: UUID): ResponseEntity<AppResponse<BadgeResponse>> {
        return buildResponse(
            message = "Badge retrieved successfully",
            data = query.get(BadgeId.of(id))
        )
    }

    @GetMapping
    @Operation(summary = "Get all badges")
    fun getAll(): ResponseEntity<AppResponse<List<BadgeResponse>>> {

        return ResponseEntity.ok(
            AppResponse(
                message = "Badges retrieved successfully",
                data = query.gatAll()
            )
        )
    }

    @PostMapping
    @Operation(summary = "Create badge")
    fun create(
        @RequestBody @Valid request: BadgeRequest
    ): ResponseEntity<AppResponse<BadgeResponse>> {
        val id = command.create(request)
        return buildResponse("Badge created successfully", query.get(id))
    }

    @PutMapping("/{badgeId}")
    @Operation(summary = "Update badge")
    fun update(
        @PathVariable badgeId: UUID,
        @RequestBody @Valid request: BadgeRequest
    ): ResponseEntity<AppResponse<BadgeResponse>> {
        val id = command.update(BadgeId.of(badgeId), request)
        return buildResponse("Badge updated successfully", query.get(id))
    }

    @DeleteMapping("/{badgeId}")
    @Operation(summary = "Delete badge")
    fun delete(@PathVariable badgeId: UUID): ResponseEntity<AppResponse<Unit>> {
        command.delete(BadgeId.of(badgeId))
        return ResponseEntity.ok(
            AppResponse(
                message = "Badge deleted successfully",
                data = null
            )
        )
    }

    private fun buildResponse(
        message: String,
        data: BadgeResponse
    ): ResponseEntity<AppResponse<BadgeResponse>> {
        return ResponseEntity.ok(
            AppResponse(
                message = message,
                data = data
            )
        )
    }
}