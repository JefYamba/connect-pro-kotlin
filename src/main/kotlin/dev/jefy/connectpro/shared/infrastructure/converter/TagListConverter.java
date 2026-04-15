package dev.jefy.connectpro.shared.infrastructure.converter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.jefy.connectpro.portfolio.domain.vo.Tag;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Jôph Yamba
 */
@Converter
public class TagListConverter implements AttributeConverter<List<Tag>, String> {

    @Override
    public String convertToDatabaseColumn(List<Tag> tags) {
        if (tags == null || tags.isEmpty()) return "";
        return tags.stream()
                .map(Tag::value)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Tag> convertToEntityAttribute(String value) {
        if (value == null || value.isBlank()) return new ArrayList<>();
        return Arrays.stream(value.split(","))
                .map(Tag::new)
                .collect(Collectors.toList());
    }
}

