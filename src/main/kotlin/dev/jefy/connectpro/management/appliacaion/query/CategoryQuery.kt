package dev.jefy.connectpro.management.appliacaion.query;

import org.jspecify.annotations.NullMarked;

import java.util.List;

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.management.domain.vo.CategoryId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface CategoryQuery {
    CategoryResponse get(CategoryId categoryId);
    List<CategoryResponse> getAll();
}
