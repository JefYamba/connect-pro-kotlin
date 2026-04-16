package dev.jefy.connectpro.portfolio.domain.vo

import dev.jefy.connectpro.user.domain.vo.Email
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded

@Embeddable
data class ContactInfo(
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "email"))
    var email: Email,
    @Column(nullable = false)
    var phone1: String,
    var phone2: String? = null,
    var websiteUrl: String? = null
) {
    init {
        require(phone1.isNotBlank()) { "phone1 must not be blank" }
    }
}