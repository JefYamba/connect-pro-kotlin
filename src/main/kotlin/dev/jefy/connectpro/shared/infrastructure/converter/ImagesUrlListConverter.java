package dev.jefy.connectpro.shared.infrastructure.converter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Jôph Yamba
 */
@Converter
public class ImagesUrlListConverter implements AttributeConverter<List<ImageUrl>, String> {

    @Override
    public String convertToDatabaseColumn(List<ImageUrl> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) return "";
        return imageUrls.stream()
                .map(ImageUrl::value)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<ImageUrl> convertToEntityAttribute(String value) {
        if (value == null || value.isBlank()) return new ArrayList<>();
        return Arrays.stream(value.split(","))
                .map(ImageUrl::new)
                .collect(Collectors.toList());
    }
}
