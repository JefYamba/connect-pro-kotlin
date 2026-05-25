package dev.jefy.connectpro.portfolio.domain.vo

import dev.jefy.connectpro.user.domain.vo.Email
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded

@Embeddable
data class Contact(
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "email"))
    var email: Email? = null,
    var phone: String? = null,
    var website: String? = null
)