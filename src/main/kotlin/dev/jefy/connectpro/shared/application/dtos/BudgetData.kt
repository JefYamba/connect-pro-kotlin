package dev.jefy.connectpro.shared.application.dtos;


import java.math.BigDecimal;

import dev.jefy.connectpro.portfolio.domain.vo.Budget;
import dev.jefy.connectpro.shared.domain.vo.PayPeriod;

/**
 * @author Jôph Yamba
 */
public record BudgetData (
    double amountFrom,
    double amountTo,
    boolean isNegociable,
    PayPeriod payPeriod
){
    public static BudgetData from(Budget budget) {
        return new BudgetData(
                budget.amountFrom().floatValue(),
                budget.amountTo().floatValue(),
                budget.isNegociable(),
                budget.payPeriod()
        );
    }

    public Budget toDomain() {
        return new Budget(
                new BigDecimal(this.amountFrom),
                new BigDecimal(this.amountTo),
                isNegociable,
                payPeriod
        );
    }
}

