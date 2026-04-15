package dev.jefy.connectpro.user.domain.vo;


import org.springframework.util.Assert;

import dev.jefy.connectpro.shared.infrastructure.ddd.DValueObject;

/**
 * @author Jôph Yamba
 */
public record Email(String value) implements DValueObject<String> {
    public Email {
        Assert.hasText(value, "Email value must not be empty");
    }

    public static Email of(String email) {
        return new  Email(email);
    }
}
