package dev.jefy.connectpro.portfolio.domain.vo;


import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record Pricing (
        BigDecimal basePrice,
        int deliveryDays,
        boolean isNegotiable,
        List<Extra> extras 
) {}

