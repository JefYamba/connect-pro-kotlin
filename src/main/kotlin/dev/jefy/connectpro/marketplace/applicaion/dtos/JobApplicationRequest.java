package dev.jefy.connectpro.marketplace.applicaion.dtos;


import java.util.UUID; /**
 * @author Jôph Yamba
 */
public record JobApplicationRequest (
        UUID jobPostId,
        String motivation
) {}
