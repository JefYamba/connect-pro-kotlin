package dev.jefy.connectpro.management.domain.repository

import dev.jefy.connectpro.management.domain.Category
import dev.jefy.connectpro.management.domain.vo.CategoryId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, CategoryId> {
    fun existsByName(name: String): Boolean
}