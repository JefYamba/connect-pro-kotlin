package dev.jefy.connectpro.management.appliacaion.dtos;


import java.util.UUID;

import dev.jefy.connectpro.management.domain.Badge;

/**
 * @author Jôph Yamba
 */
public record BadgeResponse (
        UUID id,
        String name,
        String description,
        String color
) {
    public static BadgeResponse from(Badge badge) {
        return new BadgeResponse(
                badge.getId().value(),
                badge.getName(),
                badge.getDescription(),
                badge.getColor().value()
        );
    }
}
