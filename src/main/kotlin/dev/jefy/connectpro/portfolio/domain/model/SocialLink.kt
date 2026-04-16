package dev.jefy.connectpro.portfolio.domain.model;


import org.springframework.util.Assert;

import java.util.function.Predicate;

import dev.jefy.connectpro.portfolio.applicaion.dtos.SocialLinkData;
import dev.jefy.connectpro.portfolio.domain.vo.SocialLinkId;
import dev.jefy.connectpro.portfolio.domain.vo.SocialPlatform;
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
@Table(
        name = "social_links", 
        uniqueConstraints = @UniqueConstraint(columnNames = {"portfolio_id", "platform"})
)
public class SocialLink implements DEntity<SocialLinkId, Portfolio> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private SocialLinkId id;
    
    @ManyToOne(optional = false)
    private Portfolio portfolio;
    
    @Enumerated(EnumType.STRING)
    private SocialPlatform platform;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String url;

    public SocialLink(Portfolio portfolio, SocialLinkData  data) {
        validateFields(portfolio, data);
        this.id = SocialLinkId.generate();
        this.portfolio = portfolio;
        this.platform = data.platform();
        this.name = data.name();
        this.url = data.url();
    }
    
    void update(SocialLinkData  data) {
        validateFields(this.portfolio, data);
        this.platform = data.platform();
        this.name = data.name();
        this.url = data.url();
    }
    
    private boolean socialLinkFieldExists(Portfolio portfolio,Predicate<SocialLink> predicate){
        return portfolio.getSocialLinks().stream().anyMatch(predicate);
    }
    
    private void validateFields(Portfolio portfolio, SocialLinkData data) {
        Assert.notNull(portfolio, "Portfolio must not be null");
        Assert.notNull(data.platform(), "Platform must not be null");
        Assert.hasText(data.name(), "Name must not be null or empty");
        Assert.hasText(data.url(), "Url must not be null or empty");
        if (socialLinkFieldExists(portfolio,x -> x.getPlatform().equals(data.platform()))) {
            throw  new IllegalStateException("Social Link with platform already exists");
        }
        if (socialLinkFieldExists(portfolio,x -> x.getUrl().equals(data.url()))) {
            throw  new IllegalStateException("Social Link with url already exists");
        }
        
    }

    @Override
    public Portfolio getRoot() {
        return this.portfolio;
    }
}
