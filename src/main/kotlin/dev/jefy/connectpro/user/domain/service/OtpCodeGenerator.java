package dev.jefy.connectpro.user.domain.service;


import org.springframework.stereotype.Service;

import java.time.Instant;

import dev.jefy.connectpro.user.domain.repository.TokenRepository;
import dev.jefy.connectpro.user.domain.vo.OtpCode;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@RequiredArgsConstructor
public class OtpCodeGenerator {
    private final TokenRepository tokenRepository;

    public OtpCode generate() {
        OtpCode code;
        do {
            code = OtpCode.generate();
        } while (tokenRepository.isCodeActiveAndNotValidated(code, Instant.now()));
        return code;
    }
}


