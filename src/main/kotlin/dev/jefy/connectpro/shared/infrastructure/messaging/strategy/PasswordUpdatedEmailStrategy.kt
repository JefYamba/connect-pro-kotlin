package dev.jefy.connectpro.shared.infrastructure.messaging.strategy;


import org.jspecify.annotations.NullMarked;
import org.springframework.util.Assert; /**
 * @author Jôph Yamba
 */
@NullMarked
public record PasswordUpdatedEmailStrategy(String name) implements EmailStrategy {
    @Override
    public String getMessage() {
        Assert.notNull(name, "Name must not be null");
        return String.format(
                """
                Hi %s,
                
                Your value have been updated successfully.
                
                You can now login with user email and value.
            
                The support Team.
                """.formatted(name)
        );
    }

    @Override
    public String getSubject() {
        return "Password updated successfully.";
    }
}
