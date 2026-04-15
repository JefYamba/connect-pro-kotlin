package dev.jefy.connectpro.user.presentation

import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder
import dev.jefy.connectpro.user.application.command.UserCommand
import dev.jefy.connectpro.user.application.dtos.*
import dev.jefy.connectpro.user.application.query.UserQuery
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.TokenId
import dev.jefy.connectpro.user.domain.vo.UserId
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.util.*
/**
 * @author  Jôph Yamba
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Api")
class AuthController(
    private val command: UserCommand,
    private val query: UserQuery
) {

    @GetMapping("/user")
    fun getAuthenticatedUser(): ResponseEntity<AppResponse<UserResponse>> {
        val user = query.getAuthenticatedUser()
        return AppResponseBuilder.builder<UserResponse>()
            .message("User retrieved successfully")
            .data(user)
            .build()
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid @NotNull request: LoginRequest): ResponseEntity<AppResponse<LoginResponse>> {
        val response = command.login(request)
        return AppResponseBuilder.builder<LoginResponse>()
            .message("logged in successfully")
            .data(response)
            .build()
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<AppResponse<Void>> {
        command.logout()
        return AppResponseBuilder.builder<Void>()
            .message("logged out successfully")
            .build()
    }

    @PostMapping("/create")
    fun createAccount(@RequestBody @Valid @NotNull request: CreateAccountRequest): ResponseEntity<AppResponse<TokenId>> {
        val tokenId = command.createAccount(request)
        return AppResponseBuilder.builder<TokenId>()
            .message("Account created successfully")
            .data(tokenId)
            .build()
    }

    @PatchMapping("/activate")
    fun validateAccountCreation(@RequestBody @NotNull request: ActivateAccountRequest): ResponseEntity<AppResponse<Void>> {
        command.validateAccountCreation(request)
        return AppResponseBuilder.builder<Void>()
            .message("Account activated successfully")
            .build()
    }

    @PatchMapping("/reset-password/request")
    fun requestForPasswordReset(@RequestBody @NotNull email: Email): ResponseEntity<AppResponse<TokenId>> {
        val tokenId = command.requestForPasswordReset(email)
        return AppResponseBuilder.builder<TokenId>()
            .message("value sent successfully")
            .data(tokenId)
            .build()
    }

    @PostMapping("/reset-password/validate-otp")
    fun validateResetPasswordOtp(@RequestBody @Valid @NotNull request: ValidateResetPasswordOtpRequest): ResponseEntity<AppResponse<TokenId>> {
        val tokenId = command.validateResetPasswordOtp(request)
        return AppResponseBuilder.builder<TokenId>()
            .message("value validated successfully")
            .data(tokenId)
            .build()
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody @Valid @NotNull request: ResetPasswordRequest): ResponseEntity<AppResponse<Void>> {
        command.resetPassword(request)
        return AppResponseBuilder.builder<Void>()
            .message("value reset successfully")
            .build()
    }

    @PutMapping("/{id}/profile-image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Throws(IOException::class)
    fun setProfileImage(
        @PathVariable @NotNull id: UUID,
        @RequestPart("image") @NotNull image: MultipartFile
    ): ResponseEntity<AppResponse<ImageUrl>> {
        val url = command.setProfileImage(UserId.of(id), image)
        return AppResponseBuilder.builder<ImageUrl>()
            .message("Profile image updated successfully")
            .data(url)
            .build()
    }
}