package dev.jefy.connectpro.shared.infrastructure.ddd;

import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public interface DIdentifier<T> {
    T value();
}

