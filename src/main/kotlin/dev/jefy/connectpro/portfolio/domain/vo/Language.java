package dev.jefy.connectpro.portfolio.domain.vo;

import dev.jefy.connectpro.shared.infrastructure.ddd.DValueObject;
import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record Language(String value) implements DValueObject<String> {}

