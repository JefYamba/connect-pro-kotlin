package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.application.dtos.JobPostRequest
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.shared.domain.vo.JobType
import dev.jefy.connectpro.shared.domain.vo.WorkMode
import jakarta.persistence.*

@Entity
@Table(name = "job_posts")
class JobPost(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: JobPostId = JobPostId.generate(),
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "portfolio_id"))
    var portfolioId: PortfolioId,
    var name: String,
    var description: String,
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "category_id"))
    var categoryId: CategoryId,
    @Enumerated(EnumType.STRING)
    var jobType: JobType,
    @Enumerated(EnumType.STRING)
    var workMode: WorkMode,
    var isClosed: Boolean = false,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "job_post_tags",
        joinColumns = [JoinColumn(name = "job_post_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableSet<Tag> = mutableSetOf(),
) {
    

     fun update(request: JobPostRequest, tags: Set<Tag>) {
        this.name = request.name
        this.description = request.description
        this.categoryId = CategoryId.of(request.categoryId)
        this.tags = tags.toMutableSet()
        this.jobType = request.jobType
        this.workMode = request.workMode
    }

    fun open() { this.isClosed = false }

    fun close() { this.isClosed = true }
}