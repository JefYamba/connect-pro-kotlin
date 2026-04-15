package dev.jefy.connectpro.shared.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.Budget
import dev.jefy.connectpro.shared.domain.vo.PayPeriod
import jakarta.persistence.Embeddable
import java.math.BigDecimal

data class BudgetData(
    val amountFrom: Double,
    val amountTo: Double,
    val isNegotiable: Boolean,
    val payPeriod: PayPeriod
) {
    fun toBudget(): Budget {
        return Budget(
            BigDecimal(amountFrom),
            BigDecimal(amountTo),
            isNegotiable,
            payPeriod
        )
    }
}

fun Budget.toBudgetData(): BudgetData = BudgetData(
    this.amountFrom().toDouble(),
    this.amountTo().toDouble(),
    this.isNegociable,
    this.payPeriod()
)