package dev.jefy.connectpro.portfolio.domain.vo;

import dev.jefy.connectpro.shared.infrastructure.ddd.DValueObject;

/**
 * @author Jôph Yamba
 */
public record Tag(String value) implements DValueObject<String> {}

