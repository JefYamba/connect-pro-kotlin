package dev.jefy.connectpro.management.appliacaion.command;


import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.management.appliacaion.dtos.AwardRequest;
import dev.jefy.connectpro.management.domain.vo.AwardId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface AwardCommand {
    AwardId create(AwardRequest request);
    AwardId update(AwardId awardId, AwardRequest request);
    void delete(AwardId awardId);
}

