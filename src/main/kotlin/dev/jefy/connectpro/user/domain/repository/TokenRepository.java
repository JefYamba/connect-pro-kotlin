package dev.jefy.connectpro.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

import dev.jefy.connectpro.user.domain.model.Token;
import dev.jefy.connectpro.user.domain.vo.OtpCode;
import dev.jefy.connectpro.user.domain.vo.TokenId;

/**
 * @author Jôph Yamba
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, TokenId> {
    @Query("SELECT COUNT(t) > 0 FROM Token t WHERE t.code = :code AND t.validatedAt IS NULL AND t.expireAt > :now")
    boolean isCodeActiveAndNotValidated(@Param("code") OtpCode code, @Param("now") Instant now);

    Optional<Token> findByIdAndCode(TokenId id, OtpCode code);
}
