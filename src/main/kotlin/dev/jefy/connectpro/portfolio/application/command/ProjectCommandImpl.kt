package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.portfolio.application.dtos.ProjectRequest
import dev.jefy.connectpro.portfolio.application.exceptions.ProjectAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.ProjectNotFoundException
import dev.jefy.connectpro.portfolio.domain.model.Project
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.repository.ProjectRepository
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId
import dev.jefy.connectpro.shared.application.dtos.ImageRequest
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
@Transactional
class ProjectCommandImpl(
    private val portfolioRepo: PortfolioRepository,
    private val projectRepo: ProjectRepository,
    private val imageService: ImageService
) : ProjectCommand {

    override fun create(request: ProjectRequest): ProjectId {
        val portfolioId = PortfolioId.of(request.portfolioId)

        check(portfolioRepo.existsById(portfolioId)) { "Portfolio with id $portfolioId not found" }

        val isConflict = projectRepo.isTitleConflict(portfolioId, request.title)
        if (isConflict) throw ProjectAlreadyExistsException()

        val project = Project(portfolioId, request)
        projectRepo.save(project)

        return project.id
    }

    override fun update(
        projectId: ProjectId, request: ProjectRequest
    ): ProjectId = getProject(projectId)
            .apply {
                val isConflict = projectRepo.isTitleConflict(portfolioId, request.title)
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
    override fun removeImage(projectId: ProjectId, image: ImageRequest): ProjectId =
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
        project.images.forEach { imageService.delete(it) }
        projectRepo.delete(project)
    }

    private fun getProject(projectId: ProjectId): Project = projectRepo
        .findById(projectId).orElseThrow { ProjectNotFoundException() }
    
}