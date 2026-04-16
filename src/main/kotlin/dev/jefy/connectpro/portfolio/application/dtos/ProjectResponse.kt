package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.Project
import java.time.LocalDate
import java.util.*

/**
 * @author Jôph Yamba
 */
@JvmRecord
data class ProjectResponse(
    val id: UUID,
    val portfolioId: UUID,
    val title: String,
    val description: String?,
    val imageUrls: List<String>,
    val startAt: LocalDate?,
    val completedAt: LocalDate?
) {
    companion object {
        fun fromDomain(project: Project): ProjectResponse {
            return ProjectResponse(
                id = project.id.value,
                portfolioId = project.portfolioId.value,
                title = project.title,
                description = project.description,
                imageUrls = project.imageUrls.map{ it.value }.toList(),
                startAt = project.startAt,
                completedAt = project.completedAt
            )
        }
    }
}

fun Project.toResponse(): ProjectResponse = ProjectResponse(
    id = this.id.value,
    portfolioId = this.portfolioId.value,
    title = this.title,
    description = this.description,
    imageUrls = this.imageUrls.map{ it.value }.toList(),
    startAt = this.startAt,
    completedAt = this.completedAt
)
