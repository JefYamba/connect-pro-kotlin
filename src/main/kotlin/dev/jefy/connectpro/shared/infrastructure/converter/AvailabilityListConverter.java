package dev.jefy.connectpro.shared.infrastructure.converter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.jefy.connectpro.portfolio.domain.vo.Availability;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Jôph Yamba
 */
@Converter
public class AvailabilityListConverter implements AttributeConverter<List<Availability>, String> {

    @Override
    public String convertToDatabaseColumn(List<Availability> availabilities) {
        if (availabilities == null || availabilities.isEmpty()) return "";
        return availabilities.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Availability> convertToEntityAttribute(String value) {
        if (value == null || value.isBlank()) return new ArrayList<>();
        return Arrays.stream(value.split(","))
                .map(Availability::valueOf)
                .collect(Collectors.toList());
    }
}
