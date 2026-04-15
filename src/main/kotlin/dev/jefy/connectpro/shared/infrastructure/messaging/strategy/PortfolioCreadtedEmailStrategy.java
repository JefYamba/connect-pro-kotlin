package dev.jefy.connectpro.shared.infrastructure.messaging.strategy;


import org.jspecify.annotations.NullMarked;
import org.springframework.util.Assert;

/**
 * @author Jôph Yamba
 */
@NullMarked
public record PortfolioCreadtedEmailStrategy(String name) implements EmailStrategy {
    @Override
    public String getMessage() {
        Assert.notNull(name, "Name must not be null");
        return String.format(
                """
                Hi %s,
                
                Your Portfolio have been verified successfully.
                
                The support Team.
                """.formatted(name)
        );
    }

    @Override
    public String getSubject() {
        return "Portfolio created successfully.";
    }
}
