package dev.jefy.connectpro.management.presentaion

import dev.jefy.connectpro.management.appliacaion.command.CategoryCommand
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryRequest
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.management.appliacaion.query.CategoryQuery
import dev.jefy.connectpro.management.domain.vo.CategoryId
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
@RequestMapping("/category")
@Tag(name = "Category Api")
class CategoryController(
    private val command: CategoryCommand,
    private val query: CategoryQuery
) {

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    fun get(@PathVariable id: UUID): ResponseEntity<AppResponse<CategoryResponse>> {
        return buildResponse(
            message = "Category retrieved successfully",
            data = query.get(CategoryId.of(id))
        )
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    fun getAll(): ResponseEntity<AppResponse<List<CategoryResponse>>> {
        return ResponseEntity.ok(
            AppResponse(
                message = "Categories retrieved successfully",
                data = query.getAll()
            )
        )
    }

    @PostMapping
    @Operation(summary = "Create category")
    fun create(
        @RequestBody @Valid request: CategoryRequest
    ): ResponseEntity<AppResponse<CategoryResponse>> {
        val id = command.create(request)
        return buildResponse("Category created successfully", query.get(id))
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Update category")
    fun update(
        @PathVariable categoryId: UUID,
        @RequestBody @Valid request: CategoryRequest
    ): ResponseEntity<AppResponse<CategoryResponse>> {
        val id = command.update(CategoryId.of(categoryId), request)
        return buildResponse("Category updated successfully", query.get(id))
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete category")
    fun delete(@PathVariable categoryId: UUID): ResponseEntity<AppResponse<Unit>> {
        command.delete(CategoryId.of(categoryId))
        return ResponseEntity.ok(
            AppResponse(
                message = "Category deleted successfully",
                data = null
            )
        )
    }

    private fun buildResponse(
        message: String,
        data: CategoryResponse
    ): ResponseEntity<AppResponse<CategoryResponse>> {
        return ResponseEntity.ok(
            AppResponse(
                message = message,
                data = data
            )
        )
    }
}