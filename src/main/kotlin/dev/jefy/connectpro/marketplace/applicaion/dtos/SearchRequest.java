package dev.jefy.connectpro.marketplace.applicaion.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Min;

/**
 * @author Jôph E.F. Yamba
 * @date 08/04/2026
 * @time 12:33:01
 */
/**
 * @author Jôph Yamba
 */
public record SearchRequest(
        String search,
        @Min(1)
        int page,
        @Min(10)
        int size,
        UUID categoryId
) {}
