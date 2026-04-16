package dev.jefy.connectpro.shared.infrastructure.messaging.strategy


interface EmailStrategy {
    fun message(): String
    fun subject(): String
}