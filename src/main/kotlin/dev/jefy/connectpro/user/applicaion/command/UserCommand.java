package dev.jefy.connectpro.user.applicaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.user.applicaion.dtos.*;
import dev.jefy.connectpro.user.domain.vo.Email;
import dev.jefy.connectpro.user.domain.vo.TokenId;
import dev.jefy.connectpro.user.domain.vo.UserId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface UserCommand {

    LoginResponse login(LoginRequest request);
    void logout();
    TokenId createAccount(CreateAccountRequest request);
    void validateAccountCreation(ActivateAccountRequest request);
    TokenId requestForPasswordReset(Email email);
    TokenId validateResetPasswordOtp(ValidateResetPasswordOtpRequest request);
    void resetPassword(ResetPasswordRequest request);
    ImageUrl setProfileImage(UserId userId, MultipartFile image) throws IOException;
    void changePassword(ChangePasswordRequest request);
    void updateEmail(Email email);

}
