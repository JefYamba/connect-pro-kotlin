package dev.jefy.connectpro.shared.application.dtos;


import java.math.BigDecimal;
import java.util.List;

import dev.jefy.connectpro.portfolio.domain.vo.Extra;
import dev.jefy.connectpro.portfolio.domain.vo.Pricing;

/**
 * @author Jôph Yamba
 */
public record PricingData(
        double basePrice,
        int deliveryDays,
        boolean isNegotiable,
        List<Extra> extras
) {
    public static PricingData from(Pricing pricing) {
        return new PricingData(
                pricing.basePrice().floatValue(),
                pricing.deliveryDays(),
                pricing.isNegotiable(), 
                pricing.extras()
        );
    }

    public Pricing toDomain() {
        return new Pricing(
                new BigDecimal(this.basePrice),
                this.deliveryDays,
                this.isNegotiable,
                this.extras
        );
    }
}
