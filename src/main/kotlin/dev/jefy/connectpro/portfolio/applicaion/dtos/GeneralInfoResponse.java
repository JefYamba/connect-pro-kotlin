package dev.jefy.connectpro.portfolio.applicaion.dtos;

import java.util.List;

import dev.jefy.connectpro.portfolio.domain.vo.GeneralInfo;
import dev.jefy.connectpro.portfolio.domain.vo.Language;

/**
 * @author Jôph Yamba
 */
public record GeneralInfoResponse(
        String name,
        String shortDescription,
        String longDescription,
        String coverImageUrl,
        List<String> spokenLanguages
) {
    public static GeneralInfoResponse from(GeneralInfo generalInfo) {
        String imageUrl = generalInfo.coverImageUrl() ==null
                ? null
                : generalInfo.coverImageUrl().value();
        
        return new GeneralInfoResponse(
                generalInfo.name(),
                generalInfo.shortDescription(),
                generalInfo.longDescription(),
                imageUrl,
                generalInfo.spokenLanguages()
                        .stream()
                        .map(Language::value)
                        .toList()
        );
    }
}

