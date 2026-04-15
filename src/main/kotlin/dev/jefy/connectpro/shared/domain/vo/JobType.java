package dev.jefy.connectpro.shared.domain.vo;


import dev.jefy.connectpro.shared.infrastructure.ddd.DDomainType;

/**
 * @author Jôph Yamba
 */
public enum JobType implements DDomainType {
    FULL_TIME,
    PART_TIME,
    FREELANCE,
    INTERNSHIP,
}
