package dev.jefy.connectpro.management.appliacaion.query;

import org.jspecify.annotations.NullMarked;

import java.util.List;

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse;
import dev.jefy.connectpro.management.domain.vo.BadgeId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface BadgeQuery {
    BadgeResponse get(BadgeId badgeId);
    List<BadgeResponse> getAll();
}
