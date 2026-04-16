package dev.jefy.connectpro.shared.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.Budget
import dev.jefy.connectpro.shared.domain.vo.PayPeriod
import java.math.BigDecimal

data class BudgetData(
    val amountFrom: Double,
    val amountTo: Double,
    val isNegociable: Boolean,
    val payPeriod: PayPeriod
) {
    fun toBudget(): Budget {
        return Budget(
            BigDecimal(amountFrom),
            BigDecimal(amountTo),
            isNegociable,
            payPeriod
        )
    }
}

fun Budget.toData(): BudgetData = BudgetData(
    amountFrom = this.amountFrom.toDouble(),
    amountTo = this.amountTo.toDouble(),
    isNegociable = this.isNegociable,
    payPeriod = this.payPeriod
)