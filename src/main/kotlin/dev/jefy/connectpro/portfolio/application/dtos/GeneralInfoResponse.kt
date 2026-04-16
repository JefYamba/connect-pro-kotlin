package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.GeneralInfo
import dev.jefy.connectpro.portfolio.domain.vo.Language

/**
 * @author Jôph Yamba
 */
@JvmRecord
data class GeneralInfoResponse(
    val name: String,
    val shortDescription: String,
    val longDescription: String?,
    val coverImageUrl: String?,
    val spokenLanguages: List<String>
)
fun GeneralInfo.toResponse(): GeneralInfoResponse = GeneralInfoResponse(
    name = this.name,
    shortDescription = this.shortDescription,
    longDescription = this.longDescription,
    coverImageUrl = this.coverImageUrl?.value,
    spokenLanguages = this.spokenLanguages.map { it.value }.toList()
)
