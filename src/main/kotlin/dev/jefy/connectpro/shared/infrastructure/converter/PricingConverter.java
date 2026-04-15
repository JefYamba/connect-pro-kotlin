package dev.jefy.connectpro.shared.infrastructure.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jefy.connectpro.portfolio.domain.vo.Pricing;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter; 

/**
 * @author Jôph Yamba
 */
@Converter
public class PricingConverter implements AttributeConverter<Pricing, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Pricing pricing) {
        if (pricing == null) return null;
        try {
            return objectMapper.writeValueAsString(pricing);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Pricing to JSON", e);
        }
    }

    @Override
    public Pricing convertToEntityAttribute(String value) {
        if (value == null) return null;
        try {
            return objectMapper.readValue(value, Pricing.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to Pricing", e);
        }
    }
}
