package dev.jefy.connectpro.recommandation.domain.vo;


import dev.jefy.connectpro.shared.infrastructure.ddd.DDomainType;

/**
 * @author Jôph Yamba
 */
public enum TargetType implements DDomainType {
    LIKE,
    REVIEW,
    PORTFOLIO,
    SERVICE,
    CONTACT_FOR_SERVICE,
    JOB_POST,
    JOB_APPLICATION,
}
