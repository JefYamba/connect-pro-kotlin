package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.application.dtos.JobPostRequest
import dev.jefy.connectpro.portfolio.domain.vo.*
import dev.jefy.connectpro.shared.domain.vo.JobType
import dev.jefy.connectpro.shared.domain.vo.WorkMode
import dev.jefy.connectpro.shared.infrastructure.converter.LanguageListConverter
import dev.jefy.connectpro.shared.infrastructure.converter.TagListConverter
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "job_posts")
open class JobPost(portfolioId: PortfolioId, request: JobPostRequest) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: JobPostId = JobPostId.generate()
    protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "portfolio_id"))
    var portfolioId: PortfolioId = portfolioId
    protected set

    var title: String = request.title
    protected set

    var description: String = request.description
    protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "category_id"))
    var categoryId: CategoryId = request.categoryId.let { CategoryId.of(it) }
    protected set

    @Convert(converter = TagListConverter::class)
    @Column(name = "tags")
    var tags: MutableSet<Tag> = request.tags.map { Tag(it) }.toMutableSet()
    protected set
    
    

    @Embedded
    var budget: Budget? = request.budget?.toBudget()
    protected set

    @Enumerated(EnumType.STRING)
    var jobType: JobType? = request.jobType
     protected set

    @Enumerated(EnumType.STRING)
    var workMode: WorkMode? = request.workMode
    protected set

    @Convert(converter = LanguageListConverter::class)
    @Column(name = "spoken_languages")
    var spokenLanguages: MutableList<Language> = request.spokenLanguages.map { Language(it) }.toMutableList()
    protected set

    var isClosed: Boolean = false
    protected set

    var deadline: LocalDate? = request.deadline
    

     fun update(request: JobPostRequest) {
        this.title = request.title
        this.description = request.description
        this.categoryId = CategoryId.of(request.categoryId)
        this.tags = request.tags.map { Tag(it) }.toMutableSet()
        this.budget = request.budget?.toBudget()
        this.jobType = request.jobType
        this.workMode = request.workMode
        this.spokenLanguages = request.spokenLanguages.map { Language(it) }.toMutableList()
        this.deadline = request.deadline
    }

    fun open() { this.isClosed = false }

    fun close() { this.isClosed = true }
}