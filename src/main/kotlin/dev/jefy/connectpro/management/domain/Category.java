package dev.jefy.connectpro.management.domain;

import org.springframework.util.Assert;

import java.time.Instant;

import dev.jefy.connectpro.management.domain.vo.CategoryId;
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
@Table(name = "categories")
public class Category implements DAggregateRoot<CategoryId> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "value"))
    private CategoryId id;
    
    private String name;
    
    private String description;
    
    private Instant createdAt;

    public Category(String name, String description) {
        Assert.hasText(name, "Category name must not be empty");
        this.id = CategoryId.generate();
        this.name = name;
        this.description = description;
        this.createdAt = Instant.now();
    }
    
    public void update(String name, String description){
        Assert.hasText(name, "Category name must not be empty");
        this.name = name;
        this.description = description;
    }
    
}
