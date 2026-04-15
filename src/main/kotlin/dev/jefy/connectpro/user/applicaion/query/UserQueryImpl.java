package dev.jefy.connectpro.user.applicaion.query;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.jefy.connectpro.user.applicaion.dtos.UserResponse;
import dev.jefy.connectpro.user.domain.model.AuthUser;
import dev.jefy.connectpro.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@RequiredArgsConstructor
public class UserQueryImpl implements UserQuery {
    private final UserRepository userRepository;

    @Override
    public UserResponse getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof AuthUser user) {
            return UserResponse.fromUser(user);
        }
        return null;
    }
}
