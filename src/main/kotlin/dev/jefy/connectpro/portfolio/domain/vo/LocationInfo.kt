package dev.jefy.connectpro.portfolio.domain.vo;


import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * @author Jôph Yamba
 */
@Embeddable
public record LocationInfo (
    @Column(nullable = false)
    String country,
    @Column(nullable = false)
    String city,
    String address, // Optional
    Double latitude, // Optional
    Double longitude // Optional
) {
    public LocationInfo {
        Assert.hasText(country, "country is required");
        Assert.hasText(city, "city is required");
    }
}
