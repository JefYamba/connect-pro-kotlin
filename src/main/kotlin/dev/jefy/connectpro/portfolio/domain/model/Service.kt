package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.application.dtos.FAQRequest
import dev.jefy.connectpro.portfolio.application.exceptions.FaqNotFoundException
import dev.jefy.connectpro.portfolio.domain.vo.*
import dev.jefy.connectpro.shared.domain.vo.Image
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "services")
open class Service(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: ServiceId = ServiceId.generate(),
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "portfolio_id"))
    var portfolioId: PortfolioId,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false, columnDefinition = "TEXT")
    var description: String,
    @Enumerated(EnumType.STRING)
    var status: ServiceStatus = ServiceStatus.ACTIVE,
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "category_id"))
    var categoryId: CategoryId,
    @Column(name = "cover_image")
    var coverImage: String? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "service_tags",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: MutableSet<Tag> = mutableSetOf(),
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "service_images",
        joinColumns = [JoinColumn(name = "service_id")]
    )
    @Column(name = "image")
    var images:MutableList<String> = mutableListOf(),
    @OneToMany(
        mappedBy = "service",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var faqs: MutableList<FAQ> = mutableListOf(),
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "award_id"))
    var awardId: AwardId? = null,
    @Column(name = "created_date")
    var createdDate: Instant = Instant.now(),
) {
    
    
    fun update(title: String, description: String, categoryId: CategoryId, tags: Set<Tag>) {
        this.name = title
        this.description = description
        this.categoryId = categoryId
        this.tags = tags.toMutableSet()
    }

    fun addCoverImage(image: Image) { this.coverImage = image.value }

    fun deleteCoverImage() { this.coverImage = null }

    fun addImage(image: Image) {
        check(images.size < 4) { "A service must not have more than 4 images" }
        images.add(image.value)
    }

    fun removeImage(image: Image) { images.removeIf {  it == image.value } }

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