package dev.jefy.connectpro.shared.domain.vo;


import dev.jefy.connectpro.shared.infrastructure.ddd.DValueObject;

/**
 * @author Jôph Yamba
 */
public record ImageUrl(String value) implements DValueObject<String> {}
