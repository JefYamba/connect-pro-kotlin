package dev.jefy.connectpro.management.appliacaion.command

import dev.jefy.connectpro.management.appliacaion.CategoryAlreadyExistsException
import dev.jefy.connectpro.management.appliacaion.CategoryAlreadyInUseException
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryRequest
import dev.jefy.connectpro.management.domain.Category
import dev.jefy.connectpro.management.domain.repository.CategoryRepository
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.application.exceptions.CategoryNotFoundException
import org.jspecify.annotations.NullMarked
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
class CategoryCommandImpl(
    private val categoryRepo: CategoryRepository,
    private val portfolioClient: PortfolioClient
) : CategoryCommand {

    override fun create(request: CategoryRequest): CategoryId {
        requireNotNull(request) { "request is null" }

        if (categoryRepo.existsByName(request.name)) throw CategoryAlreadyExistsException()

        val category = Category(request.name)
        categoryRepo.save(category)
        return category.id
    }

    override fun update(categoryId: CategoryId, request: CategoryRequest): CategoryId {

        val category = getCategory(categoryId)
        category.update(request.name)
        categoryRepo.save(category)
        return category.id
    }

    override fun delete(categoryId: CategoryId) {
        val category = getCategory(categoryId)

        if (portfolioClient.isCategoryInUse(categoryId)) throw CategoryAlreadyInUseException()

        categoryRepo.delete(category)
    }

    private fun getCategory(categoryId: CategoryId): Category = categoryRepo
        .findById(categoryId)
        .orElseThrow { CategoryNotFoundException() }
    
}