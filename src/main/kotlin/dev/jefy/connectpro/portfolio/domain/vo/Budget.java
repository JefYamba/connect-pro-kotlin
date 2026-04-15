package dev.jefy.connectpro.portfolio.domain.vo;


import java.math.BigDecimal;

import dev.jefy.connectpro.shared.domain.vo.PayPeriod;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record Budget (
        BigDecimal amountFrom,
        BigDecimal amountTo,
        boolean isNegociable,
        @Enumerated(EnumType.STRING)
        PayPeriod payPeriod
){}
