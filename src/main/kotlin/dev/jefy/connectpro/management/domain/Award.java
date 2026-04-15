package dev.jefy.connectpro.management.domain;


import org.springframework.util.Assert;

import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.management.domain.vo.HexColor;
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
@Table(name = "awards")
public class Award implements DAggregateRoot<AwardId> { 
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "value"))
    private AwardId id;
    
    private String name;    
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "color"))
    private HexColor color; 
    
    private String description;


    public Award(String name, HexColor color, String description) {
        validate(name, color, description);
        this.id = AwardId.generate();
        this.name = name;
        this.color = color;
        this.description = description;
    }

    public void update(String name, HexColor color, String description) {
        validate(name, color, description);
        this.name = name;
        this.color = color;
        this.description = description;
    }

    private void validate(String name, HexColor color, String description){
        Assert.notNull(name, "name cannot be null");
        Assert.notNull(color, "value cannot be null");
        Assert.notNull(description, "description cannot be null");
    }
}
/*
    Best Design 2025
    Top Logo Design
    Customer Favorite
    Most Hired Service
 */