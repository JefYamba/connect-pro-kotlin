package dev.jefy.connectpro.user.domain.vo;

import dev.jefy.connectpro.shared.infrastructure.ddd.DValueObject;

/**
 * @author Jôph Yamba
 */
public record EncodedPassword(String value) implements DValueObject<String> {}
