package dev.jefy.connectpro.management.appliacaion.dtos

import dev.jefy.connectpro.management.domain.Badge
import java.util.*


/**
 * @author Jôph Yamba
 */

data class BadgeResponse(val id: UUID, val name: String, val description: String, val color: String)

fun Badge.toResponse() = BadgeResponse(
    id = this.id.value,
    name = this.name,
    description = this.description,
    color = this.color.value
)
