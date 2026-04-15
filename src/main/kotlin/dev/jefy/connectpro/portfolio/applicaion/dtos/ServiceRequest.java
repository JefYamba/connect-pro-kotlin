package dev.jefy.connectpro.portfolio.applicaion.dtos;


import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.shared.application.dtos.PricingData;

/**
 * @author Jôph Yamba
 */
public record ServiceRequest(
    UUID portfolioId,
    String title, 
    String description,
    UUID categoryId, 
    List<String> tags, 
    PricingData pricing
) {}
