package dev.jefy.connectpro.user.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import dev.jefy.connectpro.user.domain.model.User
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.UserId

@Repository
interface UserRepository : JpaRepository<User, UserId> {
    fun findByEmail(email: Email): Optional<User>

    fun existsByEmail(email: Email): Boolean
}