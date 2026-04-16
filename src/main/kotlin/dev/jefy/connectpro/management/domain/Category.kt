package dev.jefy.connectpro.management.domain

import dev.jefy.connectpro.management.domain.vo.CategoryId
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "categories")
open class Category(name: String, description: String) {
    @AttributeOverride(name = "value", column = Column(name = "id"))
    @EmbeddedId
    var id: CategoryId = CategoryId.generate()
        protected set

    var name: String = name
        protected set

    var description: String = description
        protected set

    var createdAt: Instant = Instant.now()
        protected set
    
    init {
       require(name.isNotBlank()) { "Category name must not be blank" }
    }

    fun update(name: String, description: String) {
        require(name.isNotBlank()) { "Category name must not be blank" }
        this.name = name
        this.description = description
    }
}