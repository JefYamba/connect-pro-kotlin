package dev.jefy.connectpro.portfolio.application.command

import dev.jefy.connectpro.portfolio.application.dtos.JobPostRequest
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import org.jspecify.annotations.NullMarked

/**
 * @author Jôph Yamba
 */
@NullMarked
interface JobPostCommand {
    fun create(request: JobPostRequest): JobPostId
    fun update(jobPostId: JobPostId, request: JobPostRequest): JobPostId
    fun open(jobPostId: JobPostId): JobPostId
    fun close(jobPostId: JobPostId): JobPostId
    fun delete(jobPostId: JobPostId)
}
