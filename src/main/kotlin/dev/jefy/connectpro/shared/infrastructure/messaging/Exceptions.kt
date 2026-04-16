package dev.jefy.connectpro.shared.infrastructure.messaging

class EmailNotSentException : RuntimeException("Failed to send email. Please try again later.")

class EmailDoesNotExistsException : RuntimeException("Email not found")