package dev.jefy.connectpro.portfolio.domain.vo;


import org.springframework.util.Assert;

import java.util.List;

import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.converter.LanguageListConverter;
import jakarta.persistence.*;

/**
 * @author Jôph Yamba
 */
@Embeddable
public  record GeneralInfo (
        @Column(nullable = false)
        String name,
        @Column(nullable = false)
        String shortDescription,
        @Column(columnDefinition = "TEXT")
        String longDescription,
        @Embedded
        @AttributeOverride(name = "value", column =  @Column(name = "cover_image"))
        ImageUrl coverImageUrl,
        @Convert(converter = LanguageListConverter.class)
        @Column(name = "spoken_languages", columnDefinition = "TEXT")
        List<Language> spokenLanguages 
){
    public GeneralInfo {
            Assert.hasText(name, "name cannot be empty");
            Assert.hasText(shortDescription, "Badge short description cannot be empty");
    }
    
    public GeneralInfo forCoverImage(ImageUrl imageUrl) {
        return new GeneralInfo(
                this.name,
                this.shortDescription,
                this.longDescription,
                imageUrl,
                this.spokenLanguages
        );
    }
}

