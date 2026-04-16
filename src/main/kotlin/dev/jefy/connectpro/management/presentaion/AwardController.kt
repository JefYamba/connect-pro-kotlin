package dev.jefy.connectpro.management.presentaion

import dev.jefy.connectpro.management.appliacaion.command.AwardCommand
import dev.jefy.connectpro.management.appliacaion.dtos.AwardRequest
import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse
import dev.jefy.connectpro.management.appliacaion.query.AwardQuery
import dev.jefy.connectpro.management.domain.vo.AwardId
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
@RequestMapping("/award")
@Tag(name = "Award Api")
class AwardController(
    private val command: AwardCommand,
    private val query: AwardQuery
) {

    @GetMapping("/{id}")
    @Operation(summary = "Get award by ID")
    fun get(@PathVariable id: UUID): ResponseEntity<AppResponse<AwardResponse>> {
        return buildResponse(
            message = "Award retrieved successfully",
            data = query.get(AwardId.of(id))
        )
    }

    @GetMapping
    @Operation(summary = "Get all awards")
    fun getAll(): ResponseEntity<AppResponse<List<AwardResponse>>> {
        return ResponseEntity.ok(
            AppResponse(
                message = "Awards retrieved successfully",
                data = query.getAll()
            )
        )
    }

    @PostMapping
    @Operation(summary = "Create award")
    fun create(
        @RequestBody @Valid request: AwardRequest
    ): ResponseEntity<AppResponse<AwardResponse>> {
        val id = command.create(request)
        return buildResponse("Award created successfully", query.get(id))
    }

    @PutMapping("/{awardId}")
    @Operation(summary = "Update award")
    fun update(
        @PathVariable awardId: UUID,
        @RequestBody @Valid request: AwardRequest
    ): ResponseEntity<AppResponse<AwardResponse>> {
        val id = command.update(AwardId.of(awardId), request)
        return buildResponse("Award updated successfully", query.get(id))
    }

    @DeleteMapping("/{awardId}")
    @Operation(summary = "Delete award")
    fun delete(@PathVariable awardId: UUID): ResponseEntity<AppResponse<Unit>> {
        command.delete(AwardId.of(awardId))

        return ResponseEntity.ok(
            AppResponse(
                message = "Award deleted successfully",
                data = null
            )
        )
    }

    private fun buildResponse(
        message: String,
        data: AwardResponse
    ): ResponseEntity<AppResponse<AwardResponse>> {
        
        return ResponseEntity.ok(
            AppResponse(
                message = message,
                data = data
            )
        )
    }
}