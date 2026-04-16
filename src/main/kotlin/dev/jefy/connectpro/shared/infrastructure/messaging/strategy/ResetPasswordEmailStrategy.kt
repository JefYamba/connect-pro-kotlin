package dev.jefy.connectpro.shared.infrastructure.messaging.strategy

import dev.jefy.connectpro.user.domain.vo.OtpCode

data class ResetPasswordEmailStrategy(private val name: String, private val code: OtpCode) : EmailStrategy {
    init {
        require(name.isNotBlank()) { "Name must not be blank" }
    }
    override fun message(): String {
        return """
            Hi $name,
            
            Your verification value is:
            
            ${code.value}
            
            This value expires in 10 minutes. Do not share it with anyone.
            
            If you did not request this value, please ignore this email.
            
            The support Team.
        """.trimIndent()
    }

    override fun subject(): String = "Reset Password"
}