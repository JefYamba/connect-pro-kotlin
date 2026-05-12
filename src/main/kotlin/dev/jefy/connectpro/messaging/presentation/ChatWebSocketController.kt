package dev.jefy.connectpro.messaging.presentation


import dev.jefy.connectpro.messaging.application.ChatCommandService
import dev.jefy.connectpro.messaging.application.dtos.MessageResponse
import dev.jefy.connectpro.messaging.application.dtos.SendMessageRequest
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.Email
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller
import java.security.Principal
import java.util.*

/**
 * @author Jôph Yamba
 *
 * Flux STOMP pour l'envoi de messages :
 *
 *   Flutter publie sur : /app/chat/{conversationId}/send
 *   Payload            : { "receiverId": "uuid", "content": "..." }
 *
 *   Le controller :
 *     1. Persiste le message
 *     2. Envoie au destinataire via /user/{receiverId}/queue/messages (dans ChatCommandService)
 *     3. Retourne l'accusé à l'émetteur via /user/queue/messages-sent
 */
@Controller
class ChatWebSocketController(
    private val chatCommandService: ChatCommandService,
    private val userClient: UserClient,
) {

    @MessageMapping("/chat/{conversationId}/send")
    @SendToUser("/queue/messages-sent")
    fun sendMessage(
        @DestinationVariable conversationId: UUID,
        @Payload request: SendMessageRequest,
        principal: Principal,
    ): MessageResponse {
        val sender = userClient.getByEmail(Email(principal.name))
        return chatCommandService.sendMessage(
            conversationId = conversationId,
            senderId = sender.id,
            receiverId = request.receiverId,
            content = request.content,
        )
    }
}
 