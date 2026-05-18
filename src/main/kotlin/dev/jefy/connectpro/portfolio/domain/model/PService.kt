package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.application.dtos.FAQRequest
import dev.jefy.connectpro.portfolio.application.dtos.ServiceRequest
import dev.jefy.connectpro.portfolio.application.exceptions.FaqNotFoundException
import dev.jefy.connectpro.portfolio.domain.vo.*
import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.shared.infrastructure.converter.ImagesListConverter
import dev.jefy.connectpro.shared.infrastructure.converter.PricingConverter
import jakarta.persistence.*

@Entity
@Table(name = "portfolio_services")
open class PService(portfolioId: PortfolioId, request: ServiceRequest, tags: Set<Tag>) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
     var id: ServiceId = ServiceId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "portfolio_id"))
    var portfolioId: PortfolioId = portfolioId
        protected set

    @Column(nullable = false)
    var title: String = request.title
        protected set

    @Column(nullable = false)
    var description: String = request.description
        protected set

    @Enumerated(EnumType.STRING)
    var status: ServiceStatus = ServiceStatus.ACTIVE
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "category_id"))
    var categoryId: CategoryId = CategoryId.of(request.categoryId)
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "cover_image"))
    var coverImage: Image? = null
        protected set

    @ManyToMany(mappedBy = "services", fetch = FetchType.EAGER)
    @JoinTable(
        name = "service_tags",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableSet<Tag> = tags.toMutableSet()
        protected set

    @Convert(converter = ImagesListConverter::class)
    @Column(name = "images", columnDefinition = "TEXT")
    var images:MutableList<Image> = mutableListOf()
        protected set

    @Convert(converter = PricingConverter::class)
    @Column(name = "pricing", columnDefinition = "TEXT")
    var pricing: Pricing? = request.pricing?.toPricing()
        protected set

    @OneToMany(
        mappedBy = "service",
        cascade = [CascadeType.ALL],
        orphanRemoval = true 
    )
    var faqs: MutableList<FAQ> = mutableListOf()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "award_id"))
    var awardId: AwardId? = null
        protected set

    
    fun update(request: ServiceRequest, tags: Set<Tag>) {
        this.title = request.title
        this.description = request.description
        this.categoryId = CategoryId.of(request.categoryId)
        this.tags = tags.toMutableSet()
        this.pricing = request.pricing?.toPricing()
    }
    
    fun updateTags(tags: List<Tag>) {
        require(tags.isNotEmpty()) { "A service must have at least one tag" }
        this.tags = tags.toMutableSet()
    }

    fun addCoverImage(coverImage: Image) { this.coverImage = coverImage }

    fun deleteCoverImage() { this.coverImage = null }

    fun addImage(image: Image) {
        check(images.size < 4) { "A service must not have more than 4 images" }
        images.add(image)
    }

    fun removeImage(image: Image) { images.removeIf {  it == image } }

    fun addFaq(faq: FAQRequest) {
        require(faqs.none { it.question == faq.question }) { "${faq.question} already exists" }
        faqs.add(FAQ(this, faq))
    }

    fun removeFaq(faqId: FAQId) {
        val removed = faqs.removeIf { it.id == faqId }
        if (!removed) throw FaqNotFoundException()
    }

    fun addAwardId(award: AwardId) { this.awardId = award }

    fun activate() { this.status = ServiceStatus.ACTIVE }

    fun deactivate() { this.status = ServiceStatus.INACTIVE }

    fun isNotActive(): Boolean = this.status == ServiceStatus.INACTIVE
    
    fun removeAward() { this.awardId = null }
}