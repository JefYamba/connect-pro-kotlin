package dev.jefy.connectpro.shared.infrastructure.messaging.strategy

/**
 * Author: Jôph Yamba
 */
data class PortfolioCreatedEmailStrategy(private val name: String) : EmailStrategy {
    init {
        require(name.isNotBlank()) { "Name must not be blank" }
    }
    override fun message(): String {
        return """
            Hi $name,
            
            Your Portfolio has been verified successfully.
            
            The support Team.
        """.trimIndent()
    }

    override fun subject(): String = "Portfolio created successfully."
    
}