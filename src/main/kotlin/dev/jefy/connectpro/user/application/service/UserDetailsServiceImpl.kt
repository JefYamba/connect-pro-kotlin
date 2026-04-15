package dev.jefy.connectpro.user.application.service

import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.user.domain.model.AuthUser
import dev.jefy.connectpro.user.domain.model.User
import dev.jefy.connectpro.user.domain.model.toAuthUser
import dev.jefy.connectpro.user.domain.repository.UserRepository
import dev.jefy.connectpro.user.domain.vo.Email
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
    private val portfolioClient: PortfolioClient
) : UserDetailsService {

    private val log = LoggerFactory.getLogger(UserDetailsServiceImpl::class.java)

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(Email(username))
            .orElseThrow { UsernameNotFoundException("AppUserDetails not found") }

        val portfolioData = portfolioClient.getPortfolioSummary(user.id).orElse(null)

        log.info("Loaded user: {} with portfolio data: {}", user.email, portfolioData)

        return user.toAuthUser(portfolioData)
    }
}