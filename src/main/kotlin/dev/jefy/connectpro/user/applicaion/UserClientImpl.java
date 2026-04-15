package dev.jefy.connectpro.user.applicaion;


/**
 * @author Jôph Yamba
 */

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.user.UserClient;
import dev.jefy.connectpro.user.applicaion.dtos.UserData;
import dev.jefy.connectpro.user.applicaion.exceptions.UnauthorizedException;
import dev.jefy.connectpro.user.domain.model.AuthUser;
import dev.jefy.connectpro.user.domain.repository.UserRepository;
import dev.jefy.connectpro.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {
    private final UserRepository userRepo;
    @Override
    public UserData getData(UserId userId) {
        return userRepo.findById(userId)
                .map(UserData::fromDomain)
                .orElseThrow(()-> new ResourceNotFound("user with value: %s not found".formatted(userId.value())));
    }

    @Override
    public UserData getCurrentUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof AuthUser user) {
            return UserData.fromAuth(user);
        }
        assert principal != null;
        throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
    }
}
