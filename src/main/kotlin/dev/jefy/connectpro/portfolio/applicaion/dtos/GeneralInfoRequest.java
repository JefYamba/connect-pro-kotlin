package dev.jefy.connectpro.portfolio.applicaion.dtos;


import org.hibernate.validator.constraints.Length;

import java.util.List;

import dev.jefy.connectpro.portfolio.domain.vo.GeneralInfo;
import dev.jefy.connectpro.portfolio.domain.vo.Language;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Jôph Yamba
 */
public record GeneralInfoRequest(
        @NotBlank(message = "portfolio name is required")
        String name,
        @NotBlank(message = "short description is required")
        @Length(max = 250, message = "short description must not exceed 250 characters")
        String shortDescription,
        String longDescription,
        List<String> spokenLanguages
) {
    
    
    
    public GeneralInfo toDomain(){
        return new GeneralInfo(
                this.name,
                this.shortDescription,
                this.longDescription,
                null,
                this.spokenLanguages.stream().map(Language::new).toList()
        );
    }
}
