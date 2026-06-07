package dev.jefy.connectpro.shared.infrastructure

import dev.jefy.connectpro.marketplace.application.exception.JobApplicationNotFound
import dev.jefy.connectpro.marketplace.application.exception.JobPostNotExistOrValidException
import dev.jefy.connectpro.marketplace.application.exception.ServiceNotExistOrValidException
import dev.jefy.connectpro.portfolio.application.exceptions.AwardNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.BadgeNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.CategoryNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.FaqNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.JobPostAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.JobPostNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.ProjectAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.ProjectNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.ServiceAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.ServiceNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.SocialLinkAlreadyExistsException
import dev.jefy.connectpro.portfolio.application.exceptions.SocialNotFoundException
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.user.application.exceptions.TokenHasExpiredException
import dev.jefy.connectpro.user.application.exceptions.TokenNotFoundException
import dev.jefy.connectpro.user.application.exceptions.UnauthorizedException
import dev.jefy.connectpro.user.application.exceptions.UserNotFoundException
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.ConstraintViolationException
import org.hibernate.exception.ConstraintViolationException as HibernateConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.multipart.MaxUploadSizeExceededException
import java.io.IOException
import java.time.Instant


/**
 * @author  Jôph Yamba
 */

@RestControllerAdvice(basePackages = ["dev.jefy.connectpro"])
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // ====================== EXCEPTIONS CUSTOM ======================

    // User
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException) = createErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "User not found")

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(ex: UnauthorizedException) = createErrorResponse(HttpStatus.UNAUTHORIZED, ex.message ?: "Unauthorized")

    @ExceptionHandler(TokenNotFoundException::class, TokenHasExpiredException::class)
    fun handleTokenIssues(ex: RuntimeException) = createErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Token issue")

    // Portfolio
    @ExceptionHandler(
        PortfolioAlreadyExistsException::class,
        ProjectAlreadyExistsException::class,
        SocialLinkAlreadyExistsException::class,
        JobPostAlreadyExistsException::class,
        ServiceAlreadyExistsException::class
    )
    fun handleAlreadyExists(ex: RuntimeException) = createErrorResponse(HttpStatus.CONFLICT, ex.message ?: "Resource already exists")

    @ExceptionHandler(
        ProjectNotFoundException::class,
        AwardNotFoundException::class,
        SocialNotFoundException::class,
        PortfolioNotFoundException::class,
        FaqNotFoundException::class,
        ServiceNotFoundException::class,
        JobPostNotFoundException::class,
        CategoryNotFoundException::class,
        BadgeNotFoundException::class
    )
    fun handleNotFound(ex: RuntimeException) = createErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "Resource not found")

    // Marketplace
    @ExceptionHandler(JobPostNotExistOrValidException::class, ServiceNotExistOrValidException::class)
    fun handleMarketplaceValidation(ex: RuntimeException) = createErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Invalid resource")

    @ExceptionHandler(JobApplicationNotFound::class)
    fun handleJobApplicationNotFound(ex: JobApplicationNotFound) = createErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "Job application not found")

    // ====================== EXCEPTIONS STANDARDS ======================

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException) = createErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Invalid argument")

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException) = createErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "Entity not found")

    // Validation
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<AppResponse<Nothing>> {
        val errors = ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        log.warn("Validation failed: $errors")
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Validation error: $errors")
    }

    @ExceptionHandler(ConstraintViolationException::class, HandlerMethodValidationException::class)
    fun handleConstraint(ex: Exception) = createErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Constraint violation")

    // JPA / Database
    @ExceptionHandler(DataIntegrityViolationException::class, HibernateConstraintViolationException::class)
    fun handleDataIntegrity(ex: Exception) = createErrorResponse(HttpStatus.CONFLICT, "Data integrity violation (duplicate key or constraint)")

    // Security
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException) = createErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials")

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthentication(ex: AuthenticationException) = createErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed")

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException) = createErrorResponse(HttpStatus.FORBIDDEN, "Access denied")

    // IO / Upload
    @ExceptionHandler(IOException::class, MaxUploadSizeExceededException::class)
    fun handleIO(ex: Exception) = createErrorResponse(HttpStatus.BAD_REQUEST, "File processing error: ${ex.message}")

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMalformedJson(ex: HttpMessageNotReadableException) = createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid JSON format")

    // Catch-all
    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<AppResponse<Nothing>> {
        log.error("Unexpected error occurred", ex)
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again.")
    }
    

    @ExceptionHandler(ConstraintViolationException::class, HandlerMethodValidationException::class)
    fun handleConstraintViolation(ex: Exception): ResponseEntity<AppResponse<Nothing>> {
        log.warn("Constraint violation: {}", ex.message)
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid data: ${ex.message}")
    }
    

    // === IO / File Upload ===
    @ExceptionHandler(IOException::class, MaxUploadSizeExceededException::class)
    fun handleIOException(ex: Exception): ResponseEntity<AppResponse<Nothing>> {
        log.error("IO error", ex)
        return createErrorResponse(HttpStatus.BAD_REQUEST, "File processing error: ${ex.message}")
    }

    // === JSON / Request body ===
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<AppResponse<Nothing>> {
        log.warn("Malformed JSON: {}", ex.message)
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request format")
    }

    // === Catch-all ===
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<AppResponse<Nothing>> {
        log.error("Unexpected error", ex)
        return createErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred. Please try again later."
        )
    }

    private fun createErrorResponse(status: HttpStatus, message: String): ResponseEntity<AppResponse<Nothing>> {
        val response = AppResponse<Nothing>(
            message = message,
            data = null,
            status = status.value(),
            timestamp = Instant.now()
        )
        return ResponseEntity.status(status).body(response)
    }
}