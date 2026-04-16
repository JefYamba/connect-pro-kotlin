package dev.jefy.connectpro.portfolio.application.dtos

import jakarta.validation.constraints.NotBlank

data class FAQRequest(
    @field:NotBlank(message = "question must not be empty")
    val question: String,
    @field:NotBlank(message = "answer must not be empty")
    val answer: String
){
    init {
        require(question.isNotBlank()) { "question must not be blank" }
        require(answer.isNotBlank()) { "answer must not be blank" }
    }
}