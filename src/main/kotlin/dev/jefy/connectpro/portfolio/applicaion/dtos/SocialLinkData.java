package dev.jefy.connectpro.portfolio.applicaion.dtos;

import dev.jefy.connectpro.portfolio.domain.model.SocialLink;
import dev.jefy.connectpro.portfolio.domain.vo.SocialPlatform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Jôph Yamba
 */
public record SocialLinkData(
        @NotNull(message = "platform value is required")
        SocialPlatform platform, 
        @NotBlank(message = "name value is required")
        String name, 
        @NotBlank(message = "url value is required")
        String url
) {
    public static SocialLinkData from(SocialLink link) {
        return new SocialLinkData(link.getPlatform(),link.getName(),link.getUrl());
    }
}
