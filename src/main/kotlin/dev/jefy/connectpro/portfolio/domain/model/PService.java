package dev.jefy.connectpro.portfolio.domain.model;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.portfolio.applicaion.dtos.FAQRequest;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ServiceRequest;
import dev.jefy.connectpro.portfolio.domain.vo.*;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.converter.ImagesUrlListConverter;
import dev.jefy.connectpro.shared.infrastructure.converter.PricingConverter;
import dev.jefy.connectpro.shared.infrastructure.converter.TagListConverter;
import dev.jefy.connectpro.shared.infrastructure.ddd.DAggregateRoot;
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
@Table(name = "portfolio_services")
public class PService implements DAggregateRoot<ServiceId> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "value"))
    private ServiceId id;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "portfolio_id"))
    private PortfolioId portfolioId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ServiceStatus status;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_id"))
    private CategoryId categoryId;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "cover_image_url"))
    private ImageUrl coverImageUrl;

    @Convert(converter = TagListConverter.class)
    @Column(name = "tags")
    private final List<Tag> tags = new ArrayList<>();

    @Convert(converter = ImagesUrlListConverter.class)
    @Column(name = "image_urls")
    private final List<ImageUrl> imageUrls = new ArrayList<>();
    
    @Convert(converter = PricingConverter.class)
    @Column(name = "pricing",columnDefinition = "TEXT")
    private Pricing pricing;
    
    @OneToMany(mappedBy = "service")
    private final List<FAQ> faqs = new ArrayList<>();
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "award_id"))
    private AwardId awardId; 

    public PService(PortfolioId portfolioId, ServiceRequest request) {
        Assert.notNull(portfolioId, "id must not be null");
        Assert.notNull(request, "data must not be null");
        Assert.hasText(request.title(), "title must not be null");
        Assert.hasText(request.description(), "description must not be null");
        Assert.notNull(request.categoryId(), "categoryId must not be null");
        
        this.id = ServiceId.generate();
        this.portfolioId = portfolioId;
        this.title = request.title();
        this.description = request.description();
        this.status = ServiceStatus.ACTIVE;
        this.categoryId = CategoryId.of(request.categoryId());
        this.setTags(request.tags());
        this.coverImageUrl = null;
        this.pricing = request.pricing().toDomain();
        this.awardId = null;
    }
    
    public void update(ServiceRequest request){
        Assert.notNull(request, "data must not be null");
        Assert.hasText(request.title(), "title must not be null");
        Assert.hasText(request.description(), "description must not be null");
        Assert.notNull(request.categoryId(), "categoryId must not be null");
        
        this.title = request.title();
        this.description = request.description();
        this.categoryId = CategoryId.of(request.categoryId());
        this.setTags(request.tags());
        this.pricing = request.pricing().toDomain();
    }
    
    public void setCoverImageUrl(ImageUrl coverImageUrl) {
        Assert.notNull(coverImageUrl, "coverImageUrl must not be null");
        this.coverImageUrl = coverImageUrl;
    }
    
    public void deleteCoverImageUrl() {
        this.coverImageUrl = null;
    }
    
    public void addImage(ImageUrl imageUrl){
        Assert.notNull(imageUrl, "value must not be null");
        if (imageUrls.size() >= 4) {
            throw new IllegalStateException("A service must not have more than 4 images");
        }
        this.imageUrls.add(imageUrl);
    }
    public void removeImage(ImageUrl imageUrl){
        Assert.notNull(imageUrl, "value must not be null");
        this.imageUrls.remove(imageUrl);
    }
    
    public void addFaq(FAQRequest faq){
        Assert.notNull(faq, "faq must not be null");
        boolean isInList = this.faqs.stream()
                .anyMatch(f -> f.getQuestion().equals(faq.question()));
        if (isInList) {
            throw new IllegalArgumentException(faq.question() + " already exists");
        }
        this.faqs.add(new FAQ(this,faq));
    }
    public void removeFaq(FAQId faqId){
        Assert.notNull(faqId, "faq must not be null");
        boolean isInList = this.faqs.stream()
                .anyMatch(f -> f.getId().equals(faqId));
        if (!isInList) {
            throw  new ResourceNotFound("faq with id " + faqId + " not found");
        }
        faqs.removeIf(f -> f.getId().equals(faqId)); 
    }
    
    public void setAwardId(AwardId award){
        Assert.notNull(award, "award must not be null");
        this.awardId = award;
    }
    
    public void activate(){
        this.status = ServiceStatus.ACTIVE;
    }
    public void deactivate(){
        this.status = ServiceStatus.INACTIVE;
    }

    public boolean isNotActive() {
        return this.status == ServiceStatus.INACTIVE;
    }


    private void setTags(List<String> tags){
        Assert.notNull(tags, "tags must not be null");
        this.tags.clear();
        tags.stream()
            .map(Tag::new)
            .forEach(this.tags::add);
    }

    public void removeAward() {
        this.awardId = null;
    }
}

