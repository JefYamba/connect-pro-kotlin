package dev.jefy.connectpro.shared.infrastructure.converter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.jefy.connectpro.portfolio.domain.vo.Language;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Jôph Yamba
 */
@Converter
public class LanguageListConverter implements AttributeConverter<List<Language>, String> {

    @Override
    public String convertToDatabaseColumn(List<Language> languages) {
        if (languages == null || languages.isEmpty()) return "";
        return languages.stream()
                .map(Language::value)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Language> convertToEntityAttribute(String value) {
        if (value == null || value.isBlank()) return new ArrayList<>();
        return Arrays.stream(value.split(","))
                .map(Language::new)
                .collect(Collectors.toList());
    }
}

