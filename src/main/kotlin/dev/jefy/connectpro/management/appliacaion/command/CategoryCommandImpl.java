package dev.jefy.connectpro.management.appliacaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryRequest;
import dev.jefy.connectpro.management.domain.Category;
import dev.jefy.connectpro.management.domain.repositoty.CategoryRepository;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.shared.application.exceptions.ResourceAlreadyExists;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryCommandImpl implements CategoryCommand {
    private final CategoryRepository categoryRepo;
    private final PortfolioClient  portfolioClient;
    @Override
    public CategoryId create(CategoryRequest request) {
        Assert.notNull(request, "request is null");
        if (!categoryRepo.existsByName(request.name())){
            throw new ResourceAlreadyExists("category with name " + request.name() + " already exists");
        }
        Category category = new Category(request.name(), request.description());
        categoryRepo.save(category);
        return category.getId();
    }

    @Override
    public CategoryId update(CategoryId categoryId, CategoryRequest request) {
        Assert.notNull(request, "request is null");
        Category category = getCategory(categoryId);
        category.update(request.name(),  request.description());
        categoryRepo.save(category);
        return category.getId();
    }

    @Override
    public void delete(CategoryId categoryId) {
        Category category = getCategory(categoryId);
        if (portfolioClient.isCategoryInUse(categoryId)) {
            throw new IllegalStateException(String.format("category with id %s is already in use", categoryId));
        }
        categoryRepo.delete(category);
    }

    private Category getCategory(CategoryId categoryId) {
        Assert.notNull(categoryId, "categoryId is null");
        return categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFound("category with id " + categoryId + " not found"));
    }
}
