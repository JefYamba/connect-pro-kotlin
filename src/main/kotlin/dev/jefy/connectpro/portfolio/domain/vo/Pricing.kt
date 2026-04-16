package dev.jefy.connectpro.portfolio.domain.vo

import dev.jefy.connectpro.shared.domain.vo.ExtraType
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class Pricing(
    var basePrice: BigDecimal,
    var deliveryDays: Int,
    var isNegotiable: Boolean,
    var extras: List<Extra>
)

data class Extra(
    val type: ExtraType,
    val name: String?,
    val price: Double,
    val description: String?
) 