package dev.jefy.connectpro.portfolio.domain.vo

import java.math.BigDecimal
import dev.jefy.connectpro.shared.domain.vo.PayPeriod
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class Budget(
    var amountFrom: BigDecimal,
    var amountTo: BigDecimal,
    var isNegociable: Boolean,
    @Enumerated(EnumType.STRING)
    var payPeriod: PayPeriod
)