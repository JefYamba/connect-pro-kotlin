package dev.jefy.connectpro.user.presentation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import dev.jefy.connectpro.shared.application.dtos.AppResponse;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder;
import dev.jefy.connectpro.user.applicaion.command.UserCommand;
import dev.jefy.connectpro.user.applicaion.dtos.*;
import dev.jefy.connectpro.user.applicaion.query.UserQuery;
import dev.jefy.connectpro.user.domain.vo.Email;
import dev.jefy.connectpro.user.domain.vo.TokenId;
import dev.jefy.connectpro.user.domain.vo.UserId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Api")
@RequiredArgsConstructor
public class AuthController {
    private final UserCommand command;
    private final UserQuery query;
    

    @GetMapping("/user")
    public ResponseEntity<AppResponse<UserResponse>> getAuthenticatedUser() {
        UserResponse user = query.getAuthenticatedUser();
        return AppResponseBuilder.<UserResponse>builder()
                .message("User retrieved successfully")
                .data(user)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse<LoginResponse>> login(
            @RequestBody @Valid @NotNull LoginRequest request
    ) {
        LoginResponse response = command.login(request);
        return AppResponseBuilder.<LoginResponse>builder()
                .message("logged in successfully")
                .data(response)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<AppResponse<Void>> logout() {
        command.logout();
        return AppResponseBuilder.<Void>builder()
                .message("logged out successfully")
                .build();
    }

    @PostMapping(path = "/create")
    public ResponseEntity<AppResponse<TokenId>> createAccount(
            @RequestBody 
            @Valid 
            @NotNull 
            CreateAccountRequest request
    ) {
        TokenId tokenId = command.createAccount(request);
        return AppResponseBuilder.<TokenId>builder()
                .message("Account created successfully")
                .data(tokenId)
                .build();
    }

    @PatchMapping(path = "/activate")
    public ResponseEntity<AppResponse<Void>> validateAccountCreation(
            @RequestBody 
            @NotNull
            ActivateAccountRequest request
    ) {
        command.validateAccountCreation(request);
        return AppResponseBuilder.<Void>builder()
                .message("Account activated successfully")
                .build();
    }

    @PatchMapping(path = "/reset-password/request")
    public ResponseEntity<AppResponse<TokenId>> requestForPasswordReset(
            @RequestBody 
            @NotNull 
            Email email
    ) {
        TokenId tokenId = command.requestForPasswordReset(email);
        return AppResponseBuilder.<TokenId>builder()
                .message("value sent successfully")
                .data(tokenId)
                .build();
    }

    @PostMapping(path = "/reset-password/validate-otp")
    public ResponseEntity<AppResponse<TokenId>> validateResetPasswordOtp(
            @RequestBody 
            @Valid 
            @NotNull 
            ValidateResetPasswordOtpRequest request
    ) {
        TokenId tokenId = command.validateResetPasswordOtp(request);
        return AppResponseBuilder.<TokenId>builder()
                .message("value validated successfully")
                .data(tokenId)
                .build();
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<AppResponse<Void>> resetPassword(
            @RequestBody 
            @Valid 
            @NotNull 
            ResetPasswordRequest request
    ) {
        command.resetPassword(request);
        return AppResponseBuilder.<Void>builder()
                .message("value reset successfully")
                .build();
    }

    @PutMapping(value = "/{id}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AppResponse<ImageUrl>> setProfileImage(
            @PathVariable @NotNull UUID id,
            @RequestPart("image") @NotNull MultipartFile image
    ) throws IOException {
        ImageUrl url = command.setProfileImage(UserId.of(id), image);
        return AppResponseBuilder.<ImageUrl>builder()
                .message("Profile image updated successfully")
                .data(url)
                .build();
    }
}
