package dev.jefy.connectpro.user.domain.service

import dev.jefy.connectpro.shared.infrastructure.config.SecurityProperties
import dev.jefy.connectpro.user.application.exceptions.TokenHasExpiredException
import dev.jefy.connectpro.user.application.exceptions.TokenNotFoundException
import dev.jefy.connectpro.user.domain.model.Token
import dev.jefy.connectpro.user.domain.repository.TokenRepository
import dev.jefy.connectpro.user.domain.vo.OtpCode
import dev.jefy.connectpro.user.domain.vo.TokenId
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.stereotype.Service

/**
 * @author  Jôph Yamba
 */
@Service
class TokenManager(
    private val securityProperties: SecurityProperties,
    private val tokenRepository: TokenRepository,
    private val otpCodeGenerator: OtpCodeGenerator
) {

    fun generateAndSave(userId: UserId): Token {
        val otpCode = otpCodeGenerator.generate()
        val token = Token(userId, otpCode, securityProperties.otpToken.expirationTime)
        tokenRepository.save(token)
        return token
    }

    fun validateToken(tokenId: TokenId, code: OtpCode): Token {
        val token = tokenRepository.findByIdAndCode(tokenId, code)
            .orElseThrow { TokenNotFoundException() }
        if (token.isExpired()) {
            throw TokenHasExpiredException()
        }
        token.validate()
        return token
    }

    fun save(token: Token) {
        tokenRepository.save(token)
    }

    fun checkValidity(tokenId: TokenId): UserId {
        val token = tokenRepository.findById(tokenId)
            .orElseThrow { TokenNotFoundException() }
        if (token.isExpired()) {
            throw TokenHasExpiredException()
        }
        if (token.isNotValidated()) {
            throw IllegalStateException("token not validated")
        }
        return token.userId
    }
}