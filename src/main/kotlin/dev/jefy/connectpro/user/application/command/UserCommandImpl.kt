package dev.jefy.connectpro.user.application.command

import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService
import dev.jefy.connectpro.shared.infrastructure.messaging.EmailService
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.AccountActivatedEmailStrategy
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.AccountCreationEmailStrategy
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.PasswordUpdatedEmailStrategy
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.ResetPasswordEmailStrategy
import dev.jefy.connectpro.user.application.dtos.*
import dev.jefy.connectpro.user.application.exceptions.UserNotFoundException
import dev.jefy.connectpro.user.domain.model.AuthUser
import dev.jefy.connectpro.user.domain.model.User
import dev.jefy.connectpro.user.domain.repository.UserRepository
import dev.jefy.connectpro.user.domain.service.TokenManager
import dev.jefy.connectpro.user.domain.vo.*
import dev.jefy.connectpro.user.infrastructure.PasswordGenerator
import dev.jefy.connectpro.user.infrastructure.config.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserCommandImpl(
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
    private val emailService: EmailService,
    private val jwtService: JwtService,
    private val authManager: AuthenticationManager,
    private val passwordGenerator: PasswordGenerator,
    private val imageService: ImageService
) : UserCommand {

    override fun login(request: LoginRequest): LoginResponse {
        val auth = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email.trim(),
                request.password
            )
        )

        val user = auth.principal as AuthUser

        val claims = hashMapOf<String, Any>("id" to user.id)
        val jwt = jwtService.generateToken(claims, user)

        return LoginResponse(user.toUserResponse(), jwt)
    }

    override fun logout() {
        SecurityContextHolder.clearContext()
    }

    override fun createAccount(request: CreateAccountRequest): TokenId {
        val email = Email(request.email)
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("User with value already exists")
        }
        if (request.password != request.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }
        val user = User(email, passwordGenerator.generate(request.password), UserRole.USER, request.name)
        userRepository.save(user)

        val token = tokenManager.generateAndSave(user.id)
        emailService.sendEmail(email, AccountCreationEmailStrategy(request.name, token.code))
        return token.id
    }

    override fun validateAccountCreation(request: ActivateAccountRequest) {
        val tokenId = TokenId.of(request.tokenId)
        val otpCode = OtpCode.of(request.code)
        val token = tokenManager.validateToken(tokenId, otpCode)
        val user = getUser(token.userId)
        user.setVerified()
        userRepository.save(user)
        tokenManager.save(token)

        emailService.sendEmail(user.email, AccountActivatedEmailStrategy(user.name))
    }

    override fun requestForPasswordReset(email: Email): TokenId {
        val user = userRepository.findByEmail(email)
            .orElseThrow { UserNotFoundException() }
        if (!user.isVerified) {
            throw IllegalStateException("User is not verified")
        }
        val token = tokenManager.generateAndSave(user.id)

        emailService.sendEmail(email, ResetPasswordEmailStrategy(user.name, token.code))
        return token.id
    }

    override fun validateResetPasswordOtp(request: ValidateResetPasswordOtpRequest): TokenId {
        val token = tokenManager.validateToken(TokenId.of(request.tokenId), OtpCode.of(request.code))
        tokenManager.save(token)
        return token.id
    }

    override fun resetPassword(request: ResetPasswordRequest) {
        if (request.newPassword != request.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }
        val userId = tokenManager.checkValidity(TokenId.of(request.tokenId))

        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException() }

        val password = passwordGenerator.generate(request.newPassword)
        user.updatePassword(password)
        userRepository.save(user)

        emailService.sendEmail(user.email, PasswordUpdatedEmailStrategy(user.name))
    }

    override fun setProfileImage(userId: UserId, image: MultipartFile): ImageUrl {
        val user = getUser(userId)
        val imageUrl = imageService.save(image)
        user.changeProfileImage(imageUrl)
        userRepository.save(user)
        return imageUrl
    }

    override fun changePassword(request: ChangePasswordRequest) {
        TODO("Not yet implemented")
    }

    override fun updateEmail(email: Email) {
        TODO("Not yet implemented")
    }

    private fun getUser(userId: UserId): User {
        return userRepository.findById(userId)
            .orElseThrow { UserNotFoundException() }
    }
}