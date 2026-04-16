package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.FAQ
import java.util.*


/**
 * @author Jôph Yamba
 */
data class FAQResponse(val id: UUID, val question: String, val answer: String) 

fun FAQ.toResponse(): FAQResponse =FAQResponse(
    id = this.id.value,
    question = this.question,
    answer = this.answer
)

