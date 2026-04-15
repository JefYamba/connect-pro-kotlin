package dev.jefy.connectpro.user.applicaion.command;


import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService;
import dev.jefy.connectpro.shared.infrastructure.messaging.EmailService;
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.AccountActivatedEmailStrategy;
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.AccountCreationEmailStrategy;
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.PasswordUpdatedEmailStrategy;
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.ResetPasswordEmailStrategy;
import dev.jefy.connectpro.user.applicaion.dtos.*;
import dev.jefy.connectpro.user.domain.model.AuthUser;
import dev.jefy.connectpro.user.domain.model.Token;
import dev.jefy.connectpro.user.domain.model.User;
import dev.jefy.connectpro.user.domain.repository.TokenRepository;
import dev.jefy.connectpro.user.domain.repository.UserRepository;
import dev.jefy.connectpro.user.domain.service.TokenManager;
import dev.jefy.connectpro.user.domain.vo.*;
import dev.jefy.connectpro.user.infrastructure.PasswordGenerator;
import dev.jefy.connectpro.user.infrastructure.config.JwtService;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@RequiredArgsConstructor
public class UserCommandImpl implements UserCommand {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordGenerator passwordGenerator;
    private final ImageService imageService;

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email().trim(),
                        request.password()
                )
        );

        AuthUser user = (AuthUser) auth.getPrincipal();

        var claims = new HashMap<String, Object>();
        assert user != null;
        claims.put("id", user.id());
        String jwt = jwtService.generateToken(claims, user);
        
        return new LoginResponse(UserResponse.fromUser(user), jwt);
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public TokenId createAccount(CreateAccountRequest request) {
        Email email = new Email(request.email());
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with value already exists");
        }
        if (!request.password().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        User user = new User(email, passwordGenerator.generate(request.password()), UserRole.USER, request.name());
        userRepository.save(user);
        
        Token token = tokenManager.generateAndSave(user.getId());
        
        emailService.sendEmail(email, new AccountCreationEmailStrategy(request.name(), token.getCode()));
        return  token.getId();
    }
    
    @Override
    public void validateAccountCreation(ActivateAccountRequest request){
        TokenId tokenId = TokenId.of(request.tokenId());
        OtpCode otpCode = OtpCode.of(request.code());
        Token token = tokenManager.validateToken(tokenId,otpCode);
        User user = getUser(token.getUserId());
        user.setVerified();
        userRepository.save(user);
        tokenManager.save(token);
        
        emailService.sendEmail(user.getEmail(), new AccountActivatedEmailStrategy(user.getName()));
    }




    @Override
    public TokenId requestForPasswordReset(Email email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User with value " + email + " not found"));
        if (!user.isVerified()) {
            throw new IllegalStateException("User is not verified");
        }
        Token token = tokenManager.generateAndSave(user.getId());

        emailService.sendEmail(email, new ResetPasswordEmailStrategy(user.getName(), token.getCode()));
        return  token.getId();
    }

    @Override
    public TokenId validateResetPasswordOtp(ValidateResetPasswordOtpRequest request){
        Token token = tokenManager.validateToken(TokenId.of(request.tokenId()), OtpCode.of(request.code()));
        tokenManager.save(token);
        return token.getId();
    }
    
    @Override
    public void resetPassword(ResetPasswordRequest request) {
        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("passwords not match");
        }
        UserId userId = tokenManager.checkValidity(TokenId.of(request.tokenId()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User with value " + userId + " not found"));

        EncodedPassword password = passwordGenerator.generate(request.newPassword());
        user.updatePassword(password);
        userRepository.save(user);

        emailService.sendEmail(user.getEmail(), new PasswordUpdatedEmailStrategy(user.getName()));
    }

    @Override
    public ImageUrl setProfileImage(UserId userId, MultipartFile image) throws IOException {
        User user = getUser(userId);
        ImageUrl imageUrl = imageService.save(image);
        user.changeProfileImage(imageUrl);
        userRepository.save(user);
        return imageUrl;
    }
    

    @Override
    public void changePassword(ChangePasswordRequest request) {
        // TODO implementation
    }

    @Override
    public void updateEmail(Email email) {
        // TODO implementation
    }

    private @NonNull User getUser(UserId userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User with value " + userId.value() + " not found"));
    }

}
