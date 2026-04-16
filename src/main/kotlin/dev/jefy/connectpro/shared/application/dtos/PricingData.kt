package dev.jefy.connectpro.shared.application.dtos

import java.math.BigDecimal
import dev.jefy.connectpro.portfolio.domain.vo.Extra
import dev.jefy.connectpro.portfolio.domain.vo.Pricing


data class PricingData(
    val basePrice: Double,
    val deliveryDays: Int,
    val isNegotiable: Boolean,
    val extras: List<Extra>
) {
    fun toPricing(): Pricing {
        return Pricing(
            basePrice = BigDecimal(basePrice),
            deliveryDays = deliveryDays,
            isNegotiable = isNegotiable,
            extras = extras
        )
    }
}

fun Pricing.toData(): PricingData = PricingData(
    basePrice = this.basePrice.toDouble(),
    deliveryDays = this.deliveryDays,
    isNegotiable = this.isNegotiable,
    extras = this.extras
)