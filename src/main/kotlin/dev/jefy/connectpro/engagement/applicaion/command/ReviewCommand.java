package dev.jefy.connectpro.engagement.applicaion.command;

import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.engagement.applicaion.dtos.ReviewRequest;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface ReviewCommand {
    void createOrUpdate(ServiceId serviceId, ReviewRequest request);
    void delete(ServiceId serviceId);
}
