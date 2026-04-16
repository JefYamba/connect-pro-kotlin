package dev.jefy.connectpro.shared.infrastructure.messaging.strategy

import org.springframework.util.Assert

/**
 * Author: Jôph Yamba
 */
data class PasswordUpdatedEmailStrategy(private val name: String) : EmailStrategy {
    init {
        require(name.isNotBlank()) { "Name must not be blank" }
    }
    override fun message(): String {
        Assert.notNull(name, "Name must not be null")
        return """
            Hi $name,
            
            Your password has been updated successfully.
            
            You can now log in with your email and password.
            
            The support team.
        """.trimIndent()
    }

    override fun subject(): String = "Password updated successfully."
    
}