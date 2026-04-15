package dev.jefy.connectpro.shared.application.dtos

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val content: List<T>,
    val size: Int,
    val currentPage: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val totalPages: Int,
    val totalElements: Long,
    val numberOfElements: Int
)

fun <T : Any> Page<T>.toPageResponse(): PageResponse<T> = PageResponse(
    content = this.content,
    size = this.size,
    currentPage = this.number,
    hasNext = this.hasNext(),
    hasPrevious = this.hasPrevious(),
    totalPages = this.totalPages,
    totalElements = this.totalElements,
    numberOfElements = this.numberOfElements
)