package dev.jefy.connectpro.portfolio.domain.vo

import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import dev.jefy.connectpro.shared.infrastructure.converter.LanguageListConverter
import jakarta.persistence.*
import org.springframework.util.Assert

@Embeddable
data class GeneralInfo(
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var shortDescription: String,
    @Column(columnDefinition = "TEXT")
    var longDescription: String? = null,
    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "cover_image"))
    var coverImageUrl: ImageUrl? = null,
    @Convert(converter = LanguageListConverter::class)
    @Column(name = "spoken_languages", columnDefinition = "TEXT")
    var spokenLanguages: List<Language> = mutableListOf()
) {
    init {
        require(name.isNotBlank()) { "name must not be blank" }
        require(shortDescription.isNotBlank()) { "short description must not be blank" }
    }
}