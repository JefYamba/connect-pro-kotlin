package dev.jefy.connectpro.shared.domain.vo;

import dev.jefy.connectpro.shared.infrastructure.ddd.DDomainType;

/**
 * @author Jôph Yamba
 */
public enum Gender implements DDomainType {
    MALE("Male"),
    FEMALE("Female");

    public final String value;
    Gender(String value) {
        this.value = value;
    }
}