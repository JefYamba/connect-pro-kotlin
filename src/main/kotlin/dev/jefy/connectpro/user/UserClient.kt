package dev.jefy.connectpro.user;

import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.user.applicaion.dtos.UserData;
import dev.jefy.connectpro.user.domain.vo.UserId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface UserClient {
    UserData getData(UserId userId);
    UserData getCurrentUser();

}
