package dev.jefy.connectpro.management.appliacaion.command;

import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.management.appliacaion.dtos.BadgeRequest;
import dev.jefy.connectpro.management.domain.vo.BadgeId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface BadgeCommand {
    BadgeId create(BadgeRequest request);
    BadgeId update(BadgeId badgeId, BadgeRequest request);
    void delete(BadgeId badgeId);
}
