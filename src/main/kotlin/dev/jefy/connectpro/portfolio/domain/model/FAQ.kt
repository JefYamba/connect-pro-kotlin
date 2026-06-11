package dev.jefy.connectpro.portfolio.domain.model

import dev.jefy.connectpro.portfolio.application.dtos.FAQRequest
import dev.jefy.connectpro.portfolio.domain.vo.FAQId
import jakarta.persistence.*

@Entity
@Table(name = "faqs")
open class FAQ (service: Service, faq: FAQRequest) {

    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: FAQId = FAQId.generate()
        protected set
    @Column(nullable = false)
    var question: String = faq.question
        protected set
    @Column(nullable = false)
    var answer: String = faq.answer
        protected set
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    var service: Service = service
        protected set
    
    
    fun update(faq: FAQRequest) {
        this.question = faq.question
        this.answer = faq.answer
    }
}