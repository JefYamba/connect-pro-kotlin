package dev.jefy.connectpro.management.appliacaion.query;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.management.domain.repositoty.CategoryRepository;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryQueryImpl implements CategoryQuery {
    private final CategoryRepository categoryRepo;
    @Override
    public CategoryResponse get(CategoryId categoryId) {
        return categoryRepo
                .findById(categoryId)
                .map(CategoryResponse::fromDomain)
                .orElseThrow(()-> new ResourceNotFound(
                        "category with id: %s not found".formatted(categoryId.value())
                ));
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepo
                .findAll()
                .stream()
                .map(CategoryResponse::fromDomain)
                .toList();
    }
}
