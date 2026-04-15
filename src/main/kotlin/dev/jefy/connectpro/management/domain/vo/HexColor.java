package dev.jefy.connectpro.management.domain.vo;

import org.springframework.util.Assert;

import dev.jefy.connectpro.shared.infrastructure.ddd.DValueObject;
import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record HexColor(String value) implements DValueObject<String> {
    private static final String HEX_COLOR_REGEX = "^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$";
    public HexColor {
        Assert.notNull(value, "value cannot be null");
        Assert.isTrue(value.matches(HEX_COLOR_REGEX),
                "Invalid hex color format. Expected #RGB or #RRGGBB");
    }

    public static HexColor of(String color) {
        return new  HexColor(color);
    }
}
