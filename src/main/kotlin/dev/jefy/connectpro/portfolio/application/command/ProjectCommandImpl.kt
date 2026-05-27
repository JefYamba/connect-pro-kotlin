package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.portfolio.application.dtos.ProjectRequest
import dev.jefy.connectpro.portfolio.application.exceptions.ProjectAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.ProjectNotFoundException
import dev.jefy.connectpro.portfolio.domain.model.Project
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.repository.ProjectRepository
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId
import dev.jefy.connectpro.shared.application.dtos.ImageData
import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.shared.infrastructure.annotations.CommandService
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@CommandService
class ProjectCommandImpl(
    private val portfolioRepo: PortfolioRepository,
    private val projectRepo: ProjectRepository,
    private val imageService: ImageService
) : ProjectCommand {

    override fun create(request: ProjectRequest): ProjectId {
        val portfolioId = PortfolioId.of(request.portfolioId)

        check(portfolioRepo.existsById(portfolioId)) { "Portfolio with id $portfolioId not found" }

        val isConflict = projectRepo.isTitleConflict(
            portfolioId = portfolioId,
            title = request.name,
        )
        if (isConflict) throw ProjectAlreadyExistsException()

        val project = Project(
            portfolioId = portfolioId,
            name = request.name,
            description = request.description,
        )
        projectRepo.save(project)

        return project.id
    }

    override fun update(
        projectId: ProjectId, request: ProjectRequest
    ): ProjectId = getProject(projectId)
            .apply {
                val isConflict = projectRepo.isTitleConflictForId(
                    portfolioId = portfolioId, 
                    title = request.name, 
                    projectId = projectId
                )
                if (isConflict) throw ProjectAlreadyExistsException()
                update(request)
            }
            .also { projectRepo.save(it) }
            .id

    @Throws(IOException::class)
    override fun addImage(
        projectId: ProjectId, image: MultipartFile
    ): ProjectId = getProject(projectId)
            .apply {
                val imageUrl = imageService.save(image)
                addImage(imageUrl)
            }
            .also { projectRepo.save(it) }
            .id

    @Throws(IOException::class)
    override fun removeImage(projectId: ProjectId, image: ImageData): ProjectId =
        getProject(projectId)
            .apply {
                imageService.delete(image.getKey())
                removeImage(image.getKey())
            }
            .also { projectRepo.save(it) }
            .id

    @Throws(IOException::class)
    override fun delete(projectId: ProjectId) {
        val project = getProject(projectId)
        project.images.forEach { imageService.delete(Image(it)) }
        projectRepo.delete(project)
    }

    private fun getProject(projectId: ProjectId): Project = projectRepo
        .findById(projectId).orElseThrow { ProjectNotFoundException() }
    
}