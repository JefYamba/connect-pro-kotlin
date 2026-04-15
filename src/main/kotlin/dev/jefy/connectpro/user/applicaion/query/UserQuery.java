package dev.jefy.connectpro.user.applicaion.query;

import dev.jefy.connectpro.user.applicaion.dtos.UserResponse;

/**
 * @author Jôph Yamba
 */
public interface UserQuery {

    UserResponse getAuthenticatedUser();
}
