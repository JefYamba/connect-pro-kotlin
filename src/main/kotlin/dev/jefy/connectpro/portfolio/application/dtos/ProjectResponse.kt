package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.Project
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import java.util.*

/**
 * @author Jôph Yamba
 */
@JvmRecord
data class ProjectResponse(
    val id: UUID,
    val portfolioId: UUID,
    val name: String,
    val description: String,
    val images: List<String>,
) {
    companion object {
        fun fromDomain(project: Project, resolver: ImageUrlResolver): ProjectResponse {
            return ProjectResponse(
                id = project.id.value,
                portfolioId = project.portfolioId.value,
                name = project.name,
                description = project.description,
                images = resolver.resolve(project.images),
            )
        }
    }
}

fun Project.toResponse(resolver: ImageUrlResolver): ProjectResponse = ProjectResponse(
    id = this.id.value,
    portfolioId = this.portfolioId.value,
    name = this.name,
    description = this.description,
    images = resolver.resolve(this.images),
)
