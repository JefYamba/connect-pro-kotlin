package dev.jefy.connectpro.messaging.applicaion.dtos;


import java.util.UUID;

/**
 * @author Jôph Yamba
 */
public record ConversationRequest (UUID participant1, UUID participant2){}

