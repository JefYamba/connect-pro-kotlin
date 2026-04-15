package dev.jefy.connectpro.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import dev.jefy.connectpro.user.domain.model.User;
import dev.jefy.connectpro.user.domain.vo.Email;
import dev.jefy.connectpro.user.domain.vo.UserId;

/**
 * @author Jôph Yamba
 */
@Repository
public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findByEmail(Email email);

    boolean existsByEmail(Email email);
}
