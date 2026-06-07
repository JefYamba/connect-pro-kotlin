package dev.jefy.connectpro.shared.infrastructure

import dev.jefy.connectpro.marketplace.application.exception.JobApplicationNotFound
import dev.jefy.connectpro.marketplace.application.exception.JobPostNotExistOrValidException
import dev.jefy.connectpro.marketplace.application.exception.ServiceNotExistOrValidException
import dev.jefy.connectpro.portfolio.application.exceptions.*
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.user.application.exceptions.*
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
 * Gestionnaire global d'exceptions pour l'application Connect Pro
 * @author Jôph Yamba
 */
@RestControllerAdvice(basePackages = ["dev.jefy.connectpro"])
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // ====================== EXCEPTIONS CUSTOM ======================

    // User Module
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException) =
        createErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "Utilisateur non trouvé")

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(ex: UnauthorizedException) =
        createErrorResponse(HttpStatus.UNAUTHORIZED, ex.message ?: "Non autorisé")

    @ExceptionHandler(TokenNotFoundException::class, TokenHasExpiredException::class)
    fun handleTokenIssues(ex: RuntimeException) =
        createErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Problème avec le token")

    // Portfolio Module
    @ExceptionHandler(
        PortfolioAlreadyExistsException::class,
        ProjectAlreadyExistsException::class,
        SocialLinkAlreadyExistsException::class,
        JobPostAlreadyExistsException::class,
        ServiceAlreadyExistsException::class
    )
    fun handleAlreadyExists(ex: RuntimeException) =
        createErrorResponse(HttpStatus.CONFLICT, ex.message ?: "Ressource déjà existante")

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
    fun handleNotFound(ex: RuntimeException) =
        createErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "Ressource non trouvée")

    // Marketplace Module
    @ExceptionHandler(JobPostNotExistOrValidException::class, ServiceNotExistOrValidException::class)
    fun handleMarketplaceValidation(ex: RuntimeException) =
        createErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Ressource invalide")

    @ExceptionHandler(JobApplicationNotFound::class)
    fun handleJobApplicationNotFound(ex: JobApplicationNotFound) =
        createErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "Candidature non trouvée")

    // ====================== EXCEPTIONS STANDARDS ======================

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException) =
        createErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Argument invalide")

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException) =
        createErrorResponse(HttpStatus.NOT_FOUND, ex.message ?: "Entité non trouvée")

    // === Validation ===
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<AppResponse<Nothing>> {
        val errors = ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        log.warn("Validation failed: $errors")
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Erreur de validation : $errors")
    }

    @ExceptionHandler(ConstraintViolationException::class, HandlerMethodValidationException::class)
    fun handleConstraintViolation(ex: Exception) =
        createErrorResponse(HttpStatus.BAD_REQUEST, ex.message ?: "Violation de contrainte")

    // === JPA / Database ===
    @ExceptionHandler(DataIntegrityViolationException::class, HibernateConstraintViolationException::class)
    fun handleDataIntegrity(ex: Exception) =
        createErrorResponse(HttpStatus.CONFLICT, "Violation d'intégrité des données (clé dupliquée ou contrainte)")

    // === Security ===
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentials(ex: BadCredentialsException) =
        createErrorResponse(HttpStatus.UNAUTHORIZED, "Identifiants invalides")

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthentication(ex: AuthenticationException) =
        createErrorResponse(HttpStatus.UNAUTHORIZED, "Authentification échouée")

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException) =
        createErrorResponse(HttpStatus.FORBIDDEN, "Accès refusé")

    // === IO / Upload ===
    @ExceptionHandler(IOException::class, MaxUploadSizeExceededException::class)
    fun handleIOException(ex: Exception) =
        createErrorResponse(HttpStatus.BAD_REQUEST, "Erreur de traitement de fichier : ${ex.message}")

    // === JSON / Request body ===
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMalformedJson(ex: HttpMessageNotReadableException) =
        createErrorResponse(HttpStatus.BAD_REQUEST, "Format de requête invalide")

    // === Catch-all (doit rester en dernier) ===
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<AppResponse<Nothing>> {
        log.error("Erreur inattendue", ex)
        return createErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Une erreur inattendue est survenue. Veuillez réessayer plus tard."
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