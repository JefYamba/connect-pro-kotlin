package dev.jefy.connectpro.user.infrastructure;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.jefy.connectpro.user.domain.vo.EncodedPassword;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@RequiredArgsConstructor
public class PasswordGenerator {
    private final PasswordEncoder passwordEncoder;
    
    public EncodedPassword generate(String password) {
        return new EncodedPassword(passwordEncoder.encode(password));
    }
}
