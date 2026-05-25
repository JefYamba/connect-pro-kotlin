package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.Contact
import dev.jefy.connectpro.user.domain.vo.Email


/**
 * @author Jôph Yamba
 */
data class ContactData(val email: String?, val phone: String?, val website: String?) {
    fun toContactInfo(): Contact = Contact(
        email = Email.of(this.email),
        phone = this.phone,
        website = this.website
    )
}

fun Contact.toData(): ContactData = ContactData(
    email = this.email?.value,
    phone = this.phone,
    website = this.website
)