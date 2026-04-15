package dev.jefy.connectpro.recommandation.domain.vo;


import dev.jefy.connectpro.shared.infrastructure.ddd.DDomainType;

/**
 * @author Jôph Yamba
 */
public enum EventType implements DDomainType {
    LIKE,
    REVIEW,
    CLICK,
    VIEW,
}



