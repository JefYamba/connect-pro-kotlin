package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.ContactInfo
import dev.jefy.connectpro.user.domain.vo.Email


/**
 * @author Jôph Yamba
 */
data class ContactInfoData(val email: String, val phone1: String, val phone2: String?, val websiteUrl: String?) {
    fun toContactInfo(): ContactInfo = ContactInfo(
        email = Email.of(this.email),
        phone1 = this.phone1,
        phone2 = this.phone2,
        websiteUrl = this.websiteUrl
    )
}

fun ContactInfo.toData(): ContactInfoData = ContactInfoData(
    email = this.email.value,
    phone1 = this.phone1,
    phone2 = this.phone2,
    websiteUrl = this.websiteUrl
)