package dev.jefy.connectpro.user.applicaion.dtos;


import org.jspecify.annotations.NullMarked;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * @author Jôph Yamba
 */

@NullMarked
public record ActivateAccountRequest(@NotNull UUID tokenId, @NotNull int code) {}
