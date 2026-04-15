package dev.jefy.connectpro.portfolio.domain.model;

import org.springframework.util.Assert;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.portfolio.applicaion.dtos.SocialLinkData;
import dev.jefy.connectpro.portfolio.domain.vo.*;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.ddd.DAggregateRoot;
import dev.jefy.connectpro.user.domain.vo.UserId;
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
@Table(name = "portfolios")
public class Portfolio implements DAggregateRoot<PortfolioId> {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private PortfolioId id;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId;
    
    @Enumerated(EnumType.STRING)
    private PortfolioType type;
    
    @Enumerated(EnumType.STRING)
    private PortfolioStatus status;


    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "badge_id"))
    private BadgeId badgeId;
    
    private Instant createdAt;
    
    @Embedded
    private GeneralInfo generalInfo;
    
    @Embedded
    private ProfessionalInfo professionalInfo;
    
    @Embedded
    private ContactInfo contactInfo;
    
    @Embedded
    private LocationInfo locationInfo;
    
    @OneToMany(mappedBy = "portfolio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private final List<SocialLink> socialLinks = new ArrayList<>();

    public Portfolio(UserId userId, PortfolioType type, GeneralInfo generalInfo, ProfessionalInfo professionalInfo, ContactInfo contactInfo, LocationInfo locationInfo, List<SocialLinkData> socialLinks) {
        Assert.notNull(userId, "reviewerId cannot be null");
        Assert.notNull(type, "type cannot be null");
        Assert.notNull(generalInfo, "generalInfo cannot be null");
        Assert.notNull(professionalInfo, "professionalInfo cannot be null");
        Assert.notNull(contactInfo, "contactInfo cannot be null");
        Assert.notNull(locationInfo, "locationInfo cannot be null");
        
        this.id = PortfolioId.generate();
        this.userId = userId;
        this.type = type;
        this.status = PortfolioStatus.ACTIVE;
        this.badgeId = null;
        this.generalInfo = generalInfo;
        this.professionalInfo = professionalInfo;
        this.contactInfo = contactInfo;
        this.locationInfo = locationInfo;
        socialLinks.forEach(this::addSocialLink);
        this.createdAt = Instant.now();
    }

    public void changeType(PortfolioType newType) {
        Assert.notNull(newType, "newType cannot be null");
        this.type = newType;
    }
    
    public void activate(){
        this.status = PortfolioStatus.ACTIVE;
    }
    public void deactivate(){
        this.status = PortfolioStatus.INACTIVE;
    }
    public void block(){
        this.status = PortfolioStatus.BLOCKED;
    }
    
    public void updateGeneralInfo(GeneralInfo generalInfo) {
        Assert.notNull(generalInfo, "generalInfo cannot be null");
        this.generalInfo = generalInfo;
    }
    public  void updateProfessionalInfo(ProfessionalInfo professionalInfo) {
        Assert.notNull(professionalInfo, "professionalInfo cannot be null");
        this.professionalInfo = professionalInfo;
    }
    public void updateContactInfo(ContactInfo contactInfo) {
        Assert.notNull(contactInfo, "contactInfo cannot be null");
        this.contactInfo = contactInfo;
    }
    public void updateLocationInfo(LocationInfo locationInfo) {
        Assert.notNull(locationInfo, "locationInfo cannot be null");
        this.locationInfo = locationInfo;
    }
    
    public boolean isActive() {
        return this.status == PortfolioStatus.ACTIVE;
    }
    
    public void addSocialLink(SocialLinkData socialLink) {
        Assert.notNull(socialLink, "socialLink cannot be null");
        this.socialLinks.add(new SocialLink(this, socialLink));
    }
    public void deleteSocialLink(SocialLinkId socialLinkId) {
        Assert.notNull(socialLinkId, "socialLinkId cannot be null");
        boolean linkInList = this.socialLinks.stream()
                .anyMatch(s -> s.getId().equals(socialLinkId));
        if (!linkInList) {
            throw   new IllegalArgumentException("Social link is not in the social list");
        }
        this.socialLinks.removeIf(s -> s.getId().equals(socialLinkId));
    }
    
    public void setCoverImageUrl(ImageUrl imageUrl) {
        this.generalInfo  = this.generalInfo.forCoverImage(imageUrl);
    }
    
    public void deleteCoverImageUrl() {
        this.generalInfo  = this.generalInfo.forCoverImage(null);
    }
    
    public void setBadgeId(BadgeId badgeId) {
        Assert.notNull(badgeId, "badgeId cannot be null");
        this.badgeId = badgeId;
    }
}

