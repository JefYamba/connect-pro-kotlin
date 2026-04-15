package dev.jefy.connectpro.portfolio.applicaion.dtos;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID; /**
 * @author Jôph Yamba
 */
public record ProjectRequest(
        UUID portfolioId,
        String title,
        String description,
        List<String> imageUrls,
        LocalDate startAt,
        LocalDate completedAt
) {}
