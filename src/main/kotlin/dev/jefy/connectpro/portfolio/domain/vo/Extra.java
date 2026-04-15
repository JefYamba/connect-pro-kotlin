package dev.jefy.connectpro.portfolio.domain.vo;


import dev.jefy.connectpro.shared.domain.vo.ExtraType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * @author Jôph Yamba
 */

@Embeddable
public record Extra(
        @Enumerated(EnumType.STRING)
        ExtraType type,
        String name,
        double price,
        String description
){}
