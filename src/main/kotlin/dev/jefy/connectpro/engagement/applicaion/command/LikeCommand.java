package dev.jefy.connectpro.engagement.applicaion.command;

import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.portfolio.domain.vo.ServiceId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface LikeCommand {

    void like(ServiceId serviceId);
    void unlike(ServiceId serviceId);
}

