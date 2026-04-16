package dev.jefy.connectpro.management.domain.vo

import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class CategoryId(var value: UUID){
    companion object {
        fun generate(): CategoryId = CategoryId(UUID.randomUUID())
        fun of(value: UUID): CategoryId = CategoryId(value)
    }
}

@Embeddable
data class AwardId(var value: UUID) {
    companion object {
        fun generate(): AwardId = AwardId(UUID.randomUUID())
        fun of(awardId: UUID): AwardId = AwardId(awardId)
    }
}

@Embeddable
data class BadgeId(var value: UUID) {
    companion object {
        fun generate(): BadgeId = BadgeId(UUID.randomUUID())
        fun of(badgeId: UUID): BadgeId = BadgeId(badgeId)
        
    }
}

@Embeddable
data class HexColor(var value: String) {
    init {
        require(value.isNotBlank()) {
            "Hex color cannot be blank"
        }

        require(HEX_COLOR_REGEX.matches(value)) {
            "Invalid hex color format. Expected #RGB or #RRGGBB"
        }
    }

    companion object {
        private val HEX_COLOR_REGEX = Regex("^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$")
        fun of(color: String): HexColor = HexColor(color)
    }
}