package dev.jefy.connectpro.shared.infrastructure.messaging.strategy;


import org.jspecify.annotations.NullMarked;
import org.springframework.util.Assert;

import dev.jefy.connectpro.user.domain.vo.OtpCode;

/**
 * @author Jôph Yamba
 */
@NullMarked
public record ResetPasswordEmailStrategy (String name, OtpCode code) implements EmailStrategy {
    @Override
    public String getMessage() {
        Assert.notNull(code, "Otp Code must not be null");
        return String.format(
                """
                Hi %s,
                
                Your verification value is:
                
                %s
                
                This value expires in 10 minutes. Do not share it with anyone.
                
                If you did not request this value, please ignore this email.
                
            
                The support Team.
                """.formatted(name, code.value())
        );
    }

    @Override
    public String getSubject() {
        return "Reset Password";
    }
}

