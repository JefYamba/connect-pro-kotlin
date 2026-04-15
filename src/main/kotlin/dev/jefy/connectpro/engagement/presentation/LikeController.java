package dev.jefy.connectpro.engagement.presentation;


import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import dev.jefy.connectpro.engagement.applicaion.command.LikeCommand;
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/service/{serviceId}/likes")
@Tag(name = "Like Api")
@RequiredArgsConstructor
public class LikeController {
    private final LikeCommand command;

    @PostMapping
    public void like(@PathVariable @NotNull UUID serviceId) {
        command.like(ServiceId.of(serviceId));
    }

    @DeleteMapping
    public void unlike(@PathVariable @NotNull UUID serviceId) {
        command.unlike(new ServiceId(serviceId));
    }
}

