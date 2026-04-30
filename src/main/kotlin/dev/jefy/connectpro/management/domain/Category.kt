package dev.jefy.connectpro.management.domain

import dev.jefy.connectpro.management.domain.vo.CategoryId
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "categories")
open class Category(name: String) {
    @AttributeOverride(name = "value", column = Column(name = "id"))
    @EmbeddedId
    var id: CategoryId = CategoryId.generate()
        protected set

    var name: String = name
        protected set

    var createdAt: Instant = Instant.now()
        protected set

    fun update(name: String) {
        this.name = name
    }
}