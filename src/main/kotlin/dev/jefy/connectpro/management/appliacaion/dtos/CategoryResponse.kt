package dev.jefy.connectpro.management.appliacaion.dtos;


import java.util.UUID;

import dev.jefy.connectpro.management.domain.Category;

/**
 * @author Jôph Yamba
 */
public record CategoryResponse (
        UUID id,
        String name,
        String description
) {
    public static CategoryResponse fromDomain(Category category) {
        return new CategoryResponse(
                category.getId().value(),
                category.getName(),
                category.getDescription()
        );
    }
}

