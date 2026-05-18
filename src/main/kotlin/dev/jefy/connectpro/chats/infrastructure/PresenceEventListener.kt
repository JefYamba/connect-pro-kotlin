package dev.jefy.connectpro.chats.infrastructure

import dev.jefy.connectpro.chats.application.dtos.PresencePayload
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.Email
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.security.Principal

/**
 * @author Jôph Yamba
 *
 * Écoute les événements STOMP natifs de Spring :
 *   - SessionConnectedEvent  → user vient de se connecter
 *   - SessionDisconnectEvent → user vient de se déconnecter
 *
 * Broadcast sur /topic/presence avec { userId, online: true/false }
 */
@Component
class PresenceEventListener(
    private val messagingTemplate: SimpMessagingTemplate,
    private val userClient: UserClient,
) {

    @EventListener
    fun onConnect(event: SessionConnectedEvent) {
        val principal = event.user ?: return
        broadcast(principal, online = true)
    }

    @EventListener
    fun onDisconnect(event: SessionDisconnectEvent) {
        val principal = event.user ?: return
        broadcast(principal, online = false)
    }

    private fun broadcast(principal: Principal, online: Boolean) {
        try {
            val user = userClient.getByEmail(Email(principal.name))
            messagingTemplate.convertAndSend(
                "/topic/presence",
                PresencePayload(userId = user.id, online = online),
            )
        } catch (_: Exception) {
            // Ne pas crasher le broker si UserClient échoue
        }
    }
}