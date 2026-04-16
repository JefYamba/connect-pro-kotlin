package dev.jefy.connectpro.user.infrastructure

import dev.jefy.connectpro.user.domain.vo.EncodedPassword
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * @author  Jôph Yamba
 */
@Service
class PasswordGenerator(private val passwordEncoder: PasswordEncoder) {

    fun generate(password: String): EncodedPassword {
        val encoded = passwordEncoder.encode(password)
            ?: throw CouldNotEncodePasswordException()
        return EncodedPassword(encoded)
    }
}