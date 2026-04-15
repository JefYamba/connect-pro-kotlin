package dev.jefy.connectpro.management.domain.repositoty;

import org.springframework.stereotype.Repository;

import dev.jefy.connectpro.management.domain.Category;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface CategoryRepository extends AggregateRepository<Category, CategoryId> {
    boolean existsByName(String name);
}
