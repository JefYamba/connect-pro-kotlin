package dev.jefy.connectpro.management.domain.repository

import org.springframework.stereotype.Repository
import dev.jefy.connectpro.management.domain.Category
import dev.jefy.connectpro.management.domain.vo.CategoryId
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface CategoryRepository : JpaRepository<Category, CategoryId> {
    fun existsByName(name: String): Boolean
}