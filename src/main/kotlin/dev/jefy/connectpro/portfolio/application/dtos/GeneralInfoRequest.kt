package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.GeneralInfo
import dev.jefy.connectpro.portfolio.domain.vo.Language
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length


/**
 * @author Jôph Yamba
 */

data class GeneralInfoRequest(
    @field:NotBlank(message = "portfolio name is required") 
    val name: String,
    @field:NotBlank(message = "short description is required") 
    @Length(max = 250, message = "short description must not exceed 250 characters")
    val shortDescription:  String,
    val longDescription: String?,
    val spokenLanguages: List<String>
) {
    fun toDomain(): GeneralInfo {
        return GeneralInfo(
            name = this.name,
            shortDescription = this.shortDescription,
            longDescription = this.longDescription,
            coverImageUrl = null,
            spokenLanguages = this.spokenLanguages.stream().map{ Language.of(it) }.toList()
        )
    }
}
