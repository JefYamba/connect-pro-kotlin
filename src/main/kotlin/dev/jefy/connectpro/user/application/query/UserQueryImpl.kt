package dev.jefy.connectpro.user.application.query

import dev.jefy.connectpro.user.application.dtos.UserResponse
import dev.jefy.connectpro.user.application.dtos.toUserResponse
import dev.jefy.connectpro.user.application.exceptions.UserNotFoundException
import dev.jefy.connectpro.user.domain.model.AuthUser
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserQueryImpl : UserQuery {

    override fun getAuthenticatedUser(): UserResponse {
        val auth: Authentication? = SecurityContextHolder.getContext().authentication
        
        if (auth != null && auth.isAuthenticated && auth.principal is AuthUser) {
            val user = auth.principal as AuthUser
            return user.toUserResponse()
        }
        throw UserNotFoundException()
    }
}