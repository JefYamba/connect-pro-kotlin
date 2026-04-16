package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.portfolio.application.dtos.ProjectRequest
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId
import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import dev.jefy.connectpro.shared.infrastructure.converter.ImagesUrlListConverter
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "portfolio_projects")
open class Project (portfolioId: PortfolioId, request: ProjectRequest) {

    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: ProjectId = ProjectId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "portfolio_id"))
    var portfolioId: PortfolioId = portfolioId
        protected set

    var title: String = request.title
        protected set

    var description: String? = request.description
        protected set

    @Convert(converter = ImagesUrlListConverter::class)
    @Column(name = "images")
    var imageUrls: MutableList<ImageUrl> = mutableListOf()

    var startAt: LocalDate? = request.startAt
        protected set

    var completedAt: LocalDate? = request.completedAt
        protected set

    
    fun update(request: ProjectRequest) {
        this.title = request.title
        this.description = request.description
        this.startAt = request.startAt
        this.completedAt = request.completedAt
    }

    fun addImage(imageUrl: ImageUrl) {
        check(imageUrls.size < 4) { "A project must not have more than 4 images" }
        imageUrls.add(imageUrl)
    }

    fun removeImage(imageUrl: ImageUrl) { imageUrls.removeIf { it == imageUrl } }
}