package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.GeneralInfo
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver

/**
 * @author Jôph Yamba
 */
data class GeneralInfoResponse(
    val name: String,
    val shortDescription: String,
    val longDescription: String?,
    val coverImage: String?,
    val spokenLanguages: List<String>
)
fun GeneralInfo.toResponse(resolver: ImageUrlResolver): GeneralInfoResponse = GeneralInfoResponse(
    name = this.name,
    shortDescription = this.shortDescription,
    longDescription = this.longDescription,
    coverImage = resolver.resolve(this.coverImage),
    spokenLanguages = this.spokenLanguages.map { it.value }.toList()
)
