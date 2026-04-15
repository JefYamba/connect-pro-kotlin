package dev.jefy.connectpro.portfolio.domain.vo;


import dev.jefy.connectpro.shared.infrastructure.ddd.DDomainType;

/**
 * @author Jôph Yamba
 */
public enum PortfolioStatus implements DDomainType {
    ACTIVE,
    INACTIVE,
    BLOCKED
}



