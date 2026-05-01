package dev.jefy.connectpro.user.domain.model

import dev.jefy.connectpro.user.domain.vo.OtpCode
import dev.jefy.connectpro.user.domain.vo.TokenId
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "tokens")
open class Token(userId: UserId, code: OtpCode, otpTokenExpiration: Long) {

    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: TokenId = TokenId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "otp_code"))
    var code: OtpCode = code
        protected set

    var createdAt: Instant = Instant.now()
        protected set 
    
    var expireAt: Instant = createdAt.plusSeconds(otpTokenExpiration * 60)
        protected set

    var validatedAt: Instant? = null
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    var userId: UserId = userId
        protected set
    
    fun isExpired(): Boolean = Instant.now().isAfter(expireAt)

    fun validate() { validatedAt = Instant.now() }

    fun isNotValidated(): Boolean = validatedAt == null
}