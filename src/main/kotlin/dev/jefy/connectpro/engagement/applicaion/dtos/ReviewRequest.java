package dev.jefy.connectpro.engagement.applicaion.dtos;


/**
 * @author Jôph Yamba
 */
public record ReviewRequest(
        int rating,
        String comment
) {}
