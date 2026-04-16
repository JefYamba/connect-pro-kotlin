package dev.jefy.connectpro.portfolio.domain.vo

import dev.jefy.connectpro.shared.domain.vo.PayPeriod
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal

@Embeddable
data class Budget(
    var amountFrom: BigDecimal,
    var amountTo: BigDecimal,
    var isNegociable: Boolean,
    @Enumerated(EnumType.STRING)
    var payPeriod: PayPeriod
)