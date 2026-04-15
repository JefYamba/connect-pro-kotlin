package dev.jefy.connectpro.shared.infrastructure.ddd;
/**
 * @author Jôph Yamba
 */
public interface DEntity<ID extends DIdentifier<?>, ROOT extends DAggregateRoot<?>> {
    ID getId();
    ROOT getRoot();
}
