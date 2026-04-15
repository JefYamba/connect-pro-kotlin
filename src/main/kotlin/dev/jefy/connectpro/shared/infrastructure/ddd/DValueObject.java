package dev.jefy.connectpro.shared.infrastructure.ddd;

import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public interface DValueObject<T> {
    T value();
}
