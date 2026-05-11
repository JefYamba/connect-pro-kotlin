package dev.jefy.connectpro.user.application.command

import dev.jefy.connectpro.shared.application.dtos.ImageData
import dev.jefy.connectpro.user.application.dtos.*
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
    fun setProfileImage(userId: UserId, image: MultipartFile): ImageData

    fun changePassword(request: ChangePasswordRequest)

    fun updateEmail(email: Email)
}