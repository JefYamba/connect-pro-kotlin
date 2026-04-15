package dev.jefy.connectpro.shared.domain.vo;


import dev.jefy.connectpro.shared.infrastructure.ddd.DDomainType;

/**
 * @author Jôph Yamba
 */
public enum JobApplicationStatus implements DDomainType {
    PENDING,
    ACCEPTED,
    REJECTED
}

