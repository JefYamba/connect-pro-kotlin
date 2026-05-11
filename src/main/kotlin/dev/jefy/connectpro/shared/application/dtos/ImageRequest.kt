package dev.jefy.connectpro.shared.application.dtos

import dev.jefy.connectpro.shared.domain.vo.Image
import jakarta.validation.constraints.NotBlank

/**
 * @author  Jôph Yamba
 */
data class ImageRequest(@field:NotBlank var value: String){
    fun getKey() = Image(value.substringAfterLast("/"))
}