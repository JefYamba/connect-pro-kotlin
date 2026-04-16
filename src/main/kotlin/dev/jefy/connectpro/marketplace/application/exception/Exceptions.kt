package dev.jefy.connectpro.marketplace.application.exception


/**
 * @author Jôph Yamba
 */
class JobPostNotExistOrValidException : RuntimeException("Job Post Not Exist Or Valid")
class ServiceNotExistOrValidException : RuntimeException("Service Not Exist Or Valid")
class JobApplicationNotFound : RuntimeException("Job Application Not Found")
