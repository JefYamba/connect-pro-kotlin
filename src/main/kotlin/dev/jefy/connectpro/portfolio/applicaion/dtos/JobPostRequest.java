package dev.jefy.connectpro.portfolio.applicaion.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.shared.application.dtos.BudgetData;
import dev.jefy.connectpro.shared.domain.vo.JobType;
import dev.jefy.connectpro.shared.domain.vo.WorkMode;

/**
 * @author Jôph Yamba
 */
public record JobPostRequest (
        UUID portfolioId,
        String title,
        String description,
        UUID categoryId,
        List<String> tags,
        BudgetData budget,
        JobType jobType,
        WorkMode workMode,
        List<String> spokenLanguages,
        LocalDate deadline
) {}
