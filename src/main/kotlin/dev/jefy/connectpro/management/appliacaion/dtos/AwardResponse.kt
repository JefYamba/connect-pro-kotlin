package dev.jefy.connectpro.management.appliacaion.dtos

import dev.jefy.connectpro.management.domain.Award
import java.util.*


/**
 * @author Jôph Yamba
 */

data class AwardResponse(val id: UUID, val name: String, val description: String, val color: String)

fun Award.toResponse() =AwardResponse(
    id = this.id.value,
    name = this.name,
    description = this.description,
    color = this.color.value
)
