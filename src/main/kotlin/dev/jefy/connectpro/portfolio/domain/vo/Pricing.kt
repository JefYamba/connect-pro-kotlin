package dev.jefy.connectpro.portfolio.domain.vo

import dev.jefy.connectpro.shared.domain.vo.ExtraType
import java.math.BigDecimal

data class Pricing(
    var basePrice: BigDecimal,
    var deliveryDays: Int,
    var isNegotiable: Boolean,
    var extras: List<Extra>
)

data class Extra(
    val type: ExtraType?,
    val name: String?,
    val price: Double,
    val description: String?
) 