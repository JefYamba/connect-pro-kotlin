package dev.jefy.connectpro.shared.infrastructure.messaging.strategy

import org.springframework.util.Assert

/**
 * Author: Jôph Yamba
 */
data class AccountActivatedEmailStrategy(private val name: String) : EmailStrategy {
    init {
        require(name.isNotBlank()) { "Name must not be blank" }
    }

    override fun message(): String {
        return """
            Hi $name,
            
            Your account has been verified successfully.
            
            You can now login with your email and password.
        
            The support Team.
        """.trimIndent()
    }

    override fun subject(): String {
        return "Account Activated successfully."
    }
}