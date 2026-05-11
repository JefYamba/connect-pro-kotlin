package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.portfolio.application.dtos.ProjectRequest
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId
import dev.jefy.connectpro.shared.application.dtos.ImageRequest
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

interface ProjectCommand {
    fun create(request: ProjectRequest): ProjectId
    fun update(projectId: ProjectId, request: ProjectRequest): ProjectId
    @Throws(IOException::class)
    fun addImage(projectId: ProjectId, image: MultipartFile): ProjectId
    @Throws(IOException::class)
    fun removeImage(projectId: ProjectId, image: ImageRequest): ProjectId
    @Throws(IOException::class)
    fun delete(projectId: ProjectId)
}