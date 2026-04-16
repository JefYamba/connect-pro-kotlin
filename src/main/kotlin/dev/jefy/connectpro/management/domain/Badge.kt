package dev.jefy.connectpro.management.domain

import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.management.domain.vo.HexColor
import jakarta.persistence.*

@Entity
@Table(name = "badges")
open class Badge(name: String, color: HexColor, description: String) {

    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "value"))
    var id: BadgeId = BadgeId.generate()
        protected set

    var name: String = name
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "color"))
     var color: HexColor = color
        protected set

    var description: String = description
        protected set

    init {
        require(name.isNotBlank()) { "Badge name must not be blank" }
        require(description.isNotBlank()) { "Award color must not be blank" }
    }

    fun update(name: String, color: HexColor, description: String) {
        require(name.isNotBlank()) { "Badge name must not be blank" }
        require(description.isNotBlank()) { "Award color must not be blank" }
        this.name = name
        this.color = color
        this.description = description
    }
    
}