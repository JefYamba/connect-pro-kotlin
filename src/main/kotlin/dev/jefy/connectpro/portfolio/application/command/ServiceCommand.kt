package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.portfolio.application.dtos.FAQRequest
import dev.jefy.connectpro.portfolio.application.dtos.ServiceRequest
import dev.jefy.connectpro.portfolio.domain.vo.FAQId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

interface ServiceCommand {
    fun create(request: ServiceRequest): ServiceId
    fun update(serviceId: ServiceId, request: ServiceRequest): ServiceId
    @Throws(IOException::class)
    fun setCoverImage(serviceId: ServiceId, image: MultipartFile): ServiceId
    @Throws(IOException::class)
    fun deleteCoverImage(serviceId: ServiceId): ServiceId
    @Throws(IOException::class)
    fun addImage(serviceId: ServiceId, image: MultipartFile): ServiceId
    @Throws(IOException::class)
    fun removeImage(serviceId: ServiceId, imageUrl: ImageUrl): ServiceId
    fun addFaq(serviceId: ServiceId, request: FAQRequest): ServiceId
    fun removeFaq(serviceId: ServiceId, faqId: FAQId): ServiceId
    fun setAward(serviceId: ServiceId, awardId: AwardId): ServiceId
    fun removeAward(serviceId: ServiceId): ServiceId
    fun activateService(serviceId: ServiceId): ServiceId
    fun deactivateService(serviceId: ServiceId): ServiceId
    fun delete(serviceId: ServiceId)
}