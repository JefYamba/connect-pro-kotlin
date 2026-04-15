package dev.jefy.connectpro.user.applicaion.dtos;


import dev.jefy.connectpro.user.domain.model.AuthUser;
import dev.jefy.connectpro.user.domain.model.User;
import dev.jefy.connectpro.user.domain.vo.Email;
import dev.jefy.connectpro.user.domain.vo.UserId;
import dev.jefy.connectpro.user.domain.vo.UserRole;

/**
 * @author Jôph Yamba
 */
public record UserData (UserId id, Email email, String name, UserRole role, String imageUrl) {
    public static UserData fromDomain(User user) {
        return new  UserData(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getProfileImage().value()
        );
    }
        public static UserData fromAuth(AuthUser user) {
        return new  UserData(
                UserId.of(user.id()),
                Email.of(user.email()),
                user.name(),
                user.role(),
                user.imageUrl()
        );
    }
    
    
}
