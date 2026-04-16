package dev.jefy.connectpro.management.presentaion;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.appliacaion.command.CategoryCommand;
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryRequest;
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.management.appliacaion.query.CategoryQuery;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.shared.application.dtos.AppResponse;
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/cateory")
@Tag(name = "Category Api")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryCommand command;
    private final CategoryQuery query;

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<AppResponse<CategoryResponse>> get(@PathVariable UUID id) {
        return buildResponse(
                "Category retrieved successfully", 
                query.get(CategoryId.of(id))
        );
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<AppResponse<List<CategoryResponse>>> getAll() {
        return AppResponseBuilder.<List<CategoryResponse>>builder()
                .message("Categories retrieved successfully")
                .data(query.all)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create category")
    public ResponseEntity<AppResponse<CategoryResponse>> create(
            @RequestBody @Valid CategoryRequest request
    ) {
        CategoryId id = command.create(request);
        return buildResponse("Category created successfully", query.get(id));
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Update category")
    public ResponseEntity<AppResponse<CategoryResponse>> update(
            @PathVariable UUID categoryId,
            @RequestBody @Valid CategoryRequest request
    ) {
        CategoryId id = command.update(CategoryId.of(categoryId), request);
        return buildResponse("Category updated successfully", query.get(id));
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete category")
    public ResponseEntity<AppResponse<Void>> delete(
            @PathVariable UUID categoryId
    ) {
        command.delete(CategoryId.of(categoryId));
        return AppResponseBuilder.<Void>builder()
                .message("Category deleted successfully")
                .build();
    }

    private ResponseEntity<AppResponse<CategoryResponse>> buildResponse(
            String message,
            CategoryResponse data
    ) {
        return AppResponseBuilder.<CategoryResponse>builder()
                .message(message)
                .data(data)
                .build();
    }
}
