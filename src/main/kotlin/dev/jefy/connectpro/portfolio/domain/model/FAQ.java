package dev.jefy.connectpro.portfolio.domain.model;


import org.springframework.util.Assert;

import dev.jefy.connectpro.portfolio.applicaion.dtos.FAQRequest;
import dev.jefy.connectpro.portfolio.domain.vo.FAQId;
import dev.jefy.connectpro.shared.infrastructure.ddd.DEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "faqs")
public class FAQ implements DEntity<FAQId, PService> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private FAQId id;
    
    @Column(nullable = false)
    private String question;
    
    @Column(nullable = false)
    private String answer;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PService service;

    public FAQ(PService service, FAQRequest faq) {
        Assert.notNull(service, "service is required");
        Assert.notNull(faq, "faq is required");
        this.id = FAQId.generate();
        this.service = service;
        this.question = faq.question();
        this.answer = faq.answer();
    }
    
    public void update(FAQRequest faq) {
        Assert.notNull(faq, "faq is required");
        this.question = faq.question();
        this.answer = faq.answer();
    }

    @Override
    public PService getRoot() {
        return this.service;
    }
}
