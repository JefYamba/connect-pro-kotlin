package dev.jefy.connectpro.management.appliacaion.query


import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.management.appliacaion.dtos.toResponse
import dev.jefy.connectpro.management.domain.repository.CategoryRepository
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.application.exceptions.CategoryNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Supplier

/**
 * @author Jôph Yamba
 */

@Service
@Transactional(readOnly = true)
class CategoryQueryImpl(private val categoryRepo: CategoryRepository) : CategoryQuery {
    
    override fun get(categoryId: CategoryId): CategoryResponse = categoryRepo
            .findById(categoryId)
            .map{ it.toResponse() }
            .orElseThrow{ CategoryNotFoundException() }

    override fun getAll(): List<CategoryResponse> = categoryRepo
        .findAll()
        .map{ it.toResponse()}
    
}
