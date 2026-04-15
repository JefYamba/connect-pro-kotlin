package dev.jefy.connectpro.shared.application.dtos;


import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Jôph Yamba
 */
public record PageResponse<T>(
        List<T> content,
        int size,
        int currentPage,
        boolean hasNext,
        boolean hasPrevious,
        int totalPages,
        long totalElements,
        int numberOfElements
) {
    public PageResponse(Page<T> page) {
        this(
                page.getContent(),
                page.getSize(),
                page.getNumber(),
                page.hasNext(),
                page.hasPrevious(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumberOfElements()
        );
    }
}

