package dev.jefy.connectpro.user.application.command

import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import dev.jefy.connectpro.user.application.dtos.ActivateAccountRequest
import dev.jefy.connectpro.user.application.dtos.ChangePasswordRequest
import dev.jefy.connectpro.user.application.dtos.CreateAccountRequest
import dev.jefy.connectpro.user.application.dtos.LoginRequest
import dev.jefy.connectpro.user.application.dtos.LoginResponse
import dev.jefy.connectpro.user.application.dtos.ResetPasswordRequest
import dev.jefy.connectpro.user.application.dtos.ValidateResetPasswordOtpRequest
import dev.jefy.connectpro.user.domain.vo.Email
import dev.jefy.connectpro.user.domain.vo.TokenId
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

interface UserCommand {

    fun login(request: LoginRequest): LoginResponse

    fun logout()

    fun createAccount(request: CreateAccountRequest): TokenId

    fun validateAccountCreation(request: ActivateAccountRequest)

    fun requestForPasswordReset(email: Email): TokenId

    fun validateResetPasswordOtp(request: ValidateResetPasswordOtpRequest): TokenId

    fun resetPassword(request: ResetPasswordRequest)

    @Throws(IOException::class)
    fun setProfileImage(userId: UserId, image: MultipartFile): ImageUrl

    fun changePassword(request: ChangePasswordRequest)

    fun updateEmail(email: Email)
}