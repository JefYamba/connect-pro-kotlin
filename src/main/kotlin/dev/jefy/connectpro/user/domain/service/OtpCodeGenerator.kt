package dev.jefy.connectpro.user.domain.service

import org.springframework.stereotype.Service
import dev.jefy.connectpro.user.domain.repository.TokenRepository
import dev.jefy.connectpro.user.domain.vo.OtpCode
import java.time.Instant

@Service
class OtpCodeGenerator(private val tokenRepository: TokenRepository) {

    fun generate(): OtpCode {
        var code: OtpCode
        do {
            code = OtpCode.generate()
        } while (tokenRepository.isCodeActiveAndNotValidated(code, Instant.now()))
        return code
    }
}