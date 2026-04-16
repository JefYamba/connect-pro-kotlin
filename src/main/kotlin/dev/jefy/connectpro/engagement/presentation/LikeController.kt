package dev.jefy.connectpro.engagement.presentation


import dev.jefy.connectpro.engagement.application.command.LikeCommand
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/service/{serviceId}/likes")
@Tag(name = "Like Api")
class LikeController(private val command: LikeCommand) {

    @PostMapping
    fun like(@PathVariable serviceId: UUID) {
        command.like(ServiceId(serviceId))
    }

    @DeleteMapping
    fun unlike(@PathVariable serviceId: UUID) {
        command.unlike(ServiceId(serviceId))
    }
}

