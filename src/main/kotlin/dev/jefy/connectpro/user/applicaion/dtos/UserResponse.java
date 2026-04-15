package dev.jefy.connectpro.user.applicaion.dtos;


import java.util.UUID;

import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.user.domain.model.AuthUser;
import dev.jefy.connectpro.user.domain.vo.UserRole;

/**
 * @author Jôph Yamba
 */

public record UserResponse (
        UUID id,
        String email,
        String name,
        String imageUrl,
        UserRole role,
        PortfolioSummaryData portfolio
) {
    public static UserResponse fromUser(AuthUser user) {
        return new UserResponse(
                user.id(),
                user.email(), 
                user.name(),
                user.imageUrl(),
                user.role(),
                user.portfolio()
        );
    }
}

