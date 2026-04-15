package dev.jefy.connectpro.user.infrastructure

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import dev.jefy.connectpro.user.domain.vo.EncodedPassword
/**
 * @author  Jôph Yamba
 */
@Service
class PasswordGenerator(private val passwordEncoder: PasswordEncoder) {

    fun generate(password: String): EncodedPassword {
        return EncodedPassword(passwordEncoder.encode(password))
    }
}