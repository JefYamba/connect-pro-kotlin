package dev.jefy.connectpro.management.domain

import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.HexColor
import jakarta.persistence.*

@Entity
@Table(name = "awards")
open class Award(name: String, color: HexColor, description: String) {

    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: AwardId = AwardId.generate()
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
        require(name.isNotBlank()) { "Award name must not be blank" }
        require(description.isNotBlank()) { "Award color must not be blank" }
    }

    fun update(name: String, color: HexColor, description: String) {
        require(name.isNotBlank()) { "Award name must not be blank" }
        require(description.isNotBlank()) { "Award color must not be blank" }
        this.name = name
        this.color = color
        this.description = description
    }
}