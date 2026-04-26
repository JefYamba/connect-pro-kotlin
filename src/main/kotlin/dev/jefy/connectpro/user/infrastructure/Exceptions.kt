package dev.jefy.connectpro.user.infrastructure

/**
 * @author  Jôph Yamba
 */
class CouldNotEncodePasswordException : RuntimeException("Password encoder failed to encode the password")