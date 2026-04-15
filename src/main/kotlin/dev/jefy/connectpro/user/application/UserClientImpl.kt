package dev.jefy.connectpro.user.application

import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.application.dtos.UserData
import dev.jefy.connectpro.user.application.dtos.toUserData
import dev.jefy.connectpro.user.application.exceptions.UnauthorizedException
import dev.jefy.connectpro.user.domain.model.AuthUser
import dev.jefy.connectpro.user.domain.repository.UserRepository
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserClientImpl(
    private val userRepo: UserRepository
) : UserClient {

    override fun getData(userId: UserId): UserData {
        return userRepo.findById(userId)
            .map { it.toUserData() }
            .orElseThrow { ResourceNotFound("user with value: ${userId.value} not found") }
    }

    override fun getCurrentUser(): UserData {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("User not authenticated")

        if (!authentication.isAuthenticated) {
            throw UnauthorizedException("User not authenticated")
        }

        return when (val principal = authentication.principal) {
            null -> throw UnauthorizedException("User not authenticated")
            is AuthUser -> principal.toUserData()
            else -> throw IllegalStateException("Unsupported principal type: ${principal::class.java.name}")
        }
    }
}