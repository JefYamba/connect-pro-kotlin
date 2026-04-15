package dev.jefy.connectpro.shared.infrastructure.ddd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
/**
 * @author Jôph Yamba
 */
@NoRepositoryBean
public interface AggregateRepository<T extends DAggregateRoot<ID>, ID extends DIdentifier<?>> extends JpaRepository<T, ID> {}
