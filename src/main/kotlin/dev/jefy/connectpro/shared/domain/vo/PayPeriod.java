package dev.jefy.connectpro.shared.domain.vo;


import dev.jefy.connectpro.shared.infrastructure.ddd.DDomainType;

/**
 * @author Jôph Yamba
 */
public enum PayPeriod implements DDomainType {
    HOURLY,
    DAILY,
    WEEKLY,
    MONTHLY
}
