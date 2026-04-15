package dev.jefy.connectpro.user.domain.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.jefy.connectpro.user.applicaion.exceptions.TokenHasExpiredException;
import dev.jefy.connectpro.user.applicaion.exceptions.TokenNotFoundException;
import dev.jefy.connectpro.user.domain.model.Token;
import dev.jefy.connectpro.user.domain.repository.TokenRepository;
import dev.jefy.connectpro.user.domain.vo.OtpCode;
import dev.jefy.connectpro.user.domain.vo.TokenId;
import dev.jefy.connectpro.user.domain.vo.UserId;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@RequiredArgsConstructor
public class TokenManager {
    @Value("${security.verification-token.expiration-time}")
    private long verificationTokenExpirationTimeInMinutes;
    private final TokenRepository tokenRepository;
    private final OtpCodeGenerator otpCodeGenerator;

    public Token generateAndSave(UserId userId) {
        OtpCode otpCode = otpCodeGenerator.generate();
        Token token = new Token(userId, otpCode, verificationTokenExpirationTimeInMinutes);
        tokenRepository.save(token);
        return token;
    }

    public Token validateToken(TokenId tokenId, OtpCode code) {
        Token token = tokenRepository.findByIdAndCode(tokenId,code)
                .orElseThrow(TokenNotFoundException::new);
        if (token.isExpired()) {
            throw new TokenHasExpiredException();
        }
        token.validate();
        return token;
    }
    
    public void save(Token token) {
        tokenRepository.save(token);
    }

    public UserId checkValidity(TokenId tokenId) {
        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(TokenNotFoundException::new);
        if (token.isExpired()) {
            throw new TokenHasExpiredException();
        }
        if (token.isNotValidated()) {
            throw new IllegalStateException("token not validated");
        }
        return  token.getUserId();
    }
}
