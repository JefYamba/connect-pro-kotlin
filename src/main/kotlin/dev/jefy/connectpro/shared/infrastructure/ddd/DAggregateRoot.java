package dev.jefy.connectpro.shared.infrastructure.ddd;
/**
 * @author Jôph Yamba
 */
public interface DAggregateRoot<ID extends DIdentifier<?>> {
    ID getId();
}
