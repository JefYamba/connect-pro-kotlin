package dev.jefy.connectpro.user.presentation

import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.shared.domain.vo.ImageUrl
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
        return ResponseEntity.ok(
            AppResponse(
                message = "User retrieved successfully",
                data = user,
            )
        )
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid @NotNull request: LoginRequest): ResponseEntity<AppResponse<LoginResponse>> {
        val response = command.login(request)
        return ResponseEntity.ok(
            AppResponse(
                message = "logged in successfully",
                data = response,
            )
        )
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<AppResponse<Unit>> {
        command.logout()
        return ResponseEntity.ok(
            AppResponse(
                message = "logged in successfully",
                data = null,
            )
        )
    }

    @PostMapping("/create")
    fun createAccount(@RequestBody @Valid @NotNull request: CreateAccountRequest): ResponseEntity<AppResponse<TokenId>> {
        val tokenId = command.createAccount(request)
        return ResponseEntity.ok(
            AppResponse(
                message = "Account created successfully",
                data = tokenId,
            )
        )
    }

    @PatchMapping("/activate")
    fun validateAccountCreation(@RequestBody request: ActivateAccountRequest): ResponseEntity<AppResponse<Unit>> {
        command.validateAccountCreation(request)
        
        return ResponseEntity.ok(
            AppResponse(
                message = "Account activated successfully",
                data = null,
            )
        )
    }

    @PatchMapping("/reset-password/request")
    fun requestForPasswordReset(@RequestBody @NotNull email: Email): ResponseEntity<AppResponse<TokenId>> {
        val tokenId = command.requestForPasswordReset(email)

        return ResponseEntity.ok(
            AppResponse(
                message = "otp code sent successfully",
                data = tokenId,
            )
        )
    }

    @PostMapping("/reset-password/validate-otp")
    fun validateResetPasswordOtp(@RequestBody @Valid @NotNull request: ValidateResetPasswordOtpRequest): ResponseEntity<AppResponse<TokenId>> {
        val tokenId = command.validateResetPasswordOtp(request)

        return ResponseEntity.ok(
            AppResponse(
                message = "otp code validated successfully",
                data = tokenId,
            )
        )
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestBody @Valid request: ResetPasswordRequest): ResponseEntity<AppResponse<Unit>> {
        command.resetPassword(request)

        return ResponseEntity.ok(
            AppResponse(
                message = "password reset successfully",
                data = null,
            )
        )
    }

    @PutMapping("/{id}/profile-image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Throws(IOException::class)
    fun setProfileImage(
        @PathVariable @NotNull id: UUID,
        @RequestPart("image") @NotNull image: MultipartFile
    ): ResponseEntity<AppResponse<ImageUrl>> {
        val url = command.setProfileImage(UserId.of(id), image)

        return ResponseEntity.ok(
            AppResponse(
                message = "Profile image updated successfully",
                data = url,
            )
        )
    }
}