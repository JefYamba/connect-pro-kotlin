package dev.jefy.connectpro.user.application.exceptions

class UserNotFoundException : RuntimeException("User not found")

class UnauthorizedException(message: String?) : RuntimeException(message)

class TokenNotFoundException : RuntimeException("Token not found")

class TokenHasExpiredException : RuntimeException("Activation token has expired. A new token has been sent to the same email address")
