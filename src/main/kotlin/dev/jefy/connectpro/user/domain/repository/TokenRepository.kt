package dev.jefy.connectpro.user.domain.repository

import dev.jefy.connectpro.user.domain.model.Token
import dev.jefy.connectpro.user.domain.vo.OtpCode
import dev.jefy.connectpro.user.domain.vo.TokenId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.Optional

@Repository
interface TokenRepository : JpaRepository<Token, TokenId> {
    
    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM Token t
        WHERE t.code = :code
        AND t.validatedAt IS NULL
        AND t.expireAt > :now
    """)
    fun isCodeActiveAndNotValidated(
        @Param("code") code: OtpCode,
        @Param("now") now: Instant
    ): Boolean
    
    fun findByIdAndCode(id: TokenId, code: OtpCode): Optional<Token>
}