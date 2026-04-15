package dev.jefy.connectpro.management.appliacaion.query;

import org.jspecify.annotations.NullMarked;

import java.util.List;

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse;
import dev.jefy.connectpro.management.domain.vo.AwardId;


/**
 * @author Jôph Yamba
 */
@NullMarked
public interface AwardQuery {
    AwardResponse get(AwardId awardId);
    List<AwardResponse> getAll();
}
