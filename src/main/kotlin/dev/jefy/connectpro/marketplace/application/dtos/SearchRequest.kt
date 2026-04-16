package dev.jefy.connectpro.marketplace.application.dtos

import jakarta.validation.constraints.Min
import java.util.*

/**
 * @author Jôph Yamba
 */

data class SearchRequest(
        val search: String?, 
        val page: Int = 0, 
        val size: Int = 10, 
        val categoryId: UUID?
)
