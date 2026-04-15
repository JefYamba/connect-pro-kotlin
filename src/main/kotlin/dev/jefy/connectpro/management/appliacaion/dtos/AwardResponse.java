package dev.jefy.connectpro.management.appliacaion.dtos;


import java.util.UUID;

import dev.jefy.connectpro.management.domain.Award;

/**
 * @author Jôph Yamba
 */
public record AwardResponse (
        UUID id,
        String name,
        String description,
        String color
){
    public static AwardResponse from(Award award) {
        return new AwardResponse(
                award.getId().value(),
                award.getName(),
                award.getDescription(),
                award.getColor().value()
        );
    }
}


