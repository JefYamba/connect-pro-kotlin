package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.portfolio.application.dtos.ProjectRequest
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId
import dev.jefy.connectpro.shared.domain.vo.Image
import jakarta.persistence.*

@Entity
@Table(name = "projects")
open class Project (
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: ProjectId = ProjectId.generate(),
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "portfolio_id"))
    var portfolioId: PortfolioId,
    @Column(name = "name", length = 500)
    var name: String,
    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "project_images",
        joinColumns = [JoinColumn(name = "project_id")]
    )
    @Column(name = "image")
    var images: MutableList<String> = mutableListOf(),
) {
    
    init {
        require(name.isNotBlank()) { "name must not be blank" }
        require(description.isNotBlank()) { "description must not be blank" }
    }

    
    fun update(request: ProjectRequest) {
        this.name = request.name
        this.description = request.description
    }

    fun addImage(image: Image) {
        check(images.size < 4) { "A project must not have more than 4 images" }
        images.add(image.value)
    }

    fun removeImage(image: Image) { images.removeIf { it == image.value } }
}