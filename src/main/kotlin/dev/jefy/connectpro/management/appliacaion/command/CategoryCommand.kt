package dev.jefy.connectpro.management.appliacaion.command;

import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryRequest;
import dev.jefy.connectpro.management.domain.vo.CategoryId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface CategoryCommand {
    CategoryId create(CategoryRequest request);
    CategoryId update(CategoryId categoryId, CategoryRequest request);
    void delete(CategoryId categoryId);
}
