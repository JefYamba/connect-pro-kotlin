package dev.jefy.connectpro.user.domain.vo;

import org.springframework.util.Assert;

import java.util.Random;

import dev.jefy.connectpro.shared.infrastructure.ddd.DValueObject;
import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record OtpCode(Integer value) implements DValueObject<Integer> {
    public OtpCode {
        Assert.notNull(value, "OTP value cannot be null");
        Assert.isTrue(value >= 1000 && value <= 9999, "OTP value must be between 1000 and 9999");
    }

    public static OtpCode generate() {
        int code = new Random().nextInt(9000) + 1000;
        return new OtpCode(code);
    }

    public static OtpCode of(int code) {
        return new  OtpCode(code);
    }
}
