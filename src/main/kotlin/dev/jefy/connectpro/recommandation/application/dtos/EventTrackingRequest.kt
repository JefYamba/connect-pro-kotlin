package dev.jefy.connectpro.recommandation.application.dtos;

import java.util.UUID;

import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
import jakarta.validation.constraints.NotNull;

/**
 * @author Jôph Yamba
 */
public record EventTrackingRequest(
    @NotNull EventType eventType,
    @NotNull UUID targetId,
    @NotNull TargetType targetType
) {}
