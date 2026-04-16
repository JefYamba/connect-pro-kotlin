package dev.jefy.connectpro.engagement.application.command

import dev.jefy.connectpro.portfolio.domain.vo.ServiceId

/**
 * @author Jôph Yamba
 */

interface LikeCommand {
    fun like(serviceId: ServiceId)
    fun unlike(serviceId: ServiceId)
}

