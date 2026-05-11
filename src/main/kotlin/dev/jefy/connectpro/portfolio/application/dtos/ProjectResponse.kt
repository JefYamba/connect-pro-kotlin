package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.Project
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
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
    val images: List<String>,
    val startAt: LocalDate?,
    val completedAt: LocalDate?
) {
    companion object {
        fun fromDomain(project: Project, resolver: ImageUrlResolver): ProjectResponse {
            return ProjectResponse(
                id = project.id.value,
                portfolioId = project.portfolioId.value,
                title = project.title,
                description = project.description,
                images = resolver.resolve(project.images),
                startAt = project.startAt,
                completedAt = project.completedAt
            )
        }
    }
}

fun Project.toResponse(resolver: ImageUrlResolver): ProjectResponse = ProjectResponse(
    id = this.id.value,
    portfolioId = this.portfolioId.value,
    title = this.title,
    description = this.description,
    images = resolver.resolve(this.images),
    startAt = this.startAt,
    completedAt = this.completedAt
)
