package dev.jefy.connectpro.chats.presentation

import dev.jefy.connectpro.chats.application.ChatCommandService
import dev.jefy.connectpro.chats.application.ChatQueryService
import dev.jefy.connectpro.chats.application.dtos.ConversationResponse
import dev.jefy.connectpro.chats.application.dtos.CreateConversationRequest
import dev.jefy.connectpro.chats.application.dtos.MessageResponse
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.user.UserClient
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/conversations")
class ChatController(
    private val chatQueryService: ChatQueryService,
    private val chatCommandService: ChatCommandService,
    private val userClient: UserClient,
) {

    @GetMapping
    fun getConversations(): AppResponse<List<ConversationResponse>> {
        val currentUser = userClient.getCurrentUser()
        return AppResponse(
            message = "Conversations retrieved successfully",
            data = chatQueryService.getConversations(currentUser.id)
        )
    }

    @GetMapping("/search")
    fun searchConversations(@RequestParam q: String): AppResponse<List<ConversationResponse>> {
        val currentUser = userClient.getCurrentUser()
        return AppResponse(
            message = "Conversations retrieved successfully",
            data = chatQueryService.searchConversations(currentUser.id, q)
        )
    }

    @PostMapping
    fun getOrCreateConversation(
        @RequestBody request: CreateConversationRequest,
    ): AppResponse<Map<String, UUID>> {
        val currentUser = userClient.getCurrentUser()
        val conversationId = chatCommandService.getOrCreateConversation(currentUser.id, request.participantId)
        return AppResponse(
            message = "Conversation retrieved or created successfully",
            data = mapOf("conversationId" to conversationId)
        )
    }

    @GetMapping("/{conversationId}/messages")
    fun getMessages(@PathVariable conversationId: UUID): AppResponse<List<MessageResponse>> {
        val currentUser = userClient.getCurrentUser()
        return AppResponse(
            message = "Messages retrieved successfully",
            data = chatQueryService.getMessages(conversationId, currentUser.id)
        )
    }

    @PostMapping("/{conversationId}/read")
    fun markAsRead(@PathVariable conversationId: UUID): AppResponse<Unit> {
        val currentUser = userClient.getCurrentUser()
        chatCommandService.markAsRead(conversationId, currentUser.id)
        return AppResponse(message = "Messages marked as read", data = null)
    }
}

/*
package dev.jefy.connectpro.messaging.presentation

import dev.jefy.connectpro.messaging.application.ChatCommandService
import dev.jefy.connectpro.messaging.application.ChatQueryService
import dev.jefy.connectpro.messaging.application.dtos.ConversationResponse
import dev.jefy.connectpro.messaging.application.dtos.CreateConversationRequest
import dev.jefy.connectpro.messaging.application.dtos.MessageResponse
import dev.jefy.connectpro.user.UserClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

*/
/**
 * @author Jôph Yamba
 *
 * REST endpoints pour le chat :
 *   GET  /api/conversations                    → liste des conversations
 *   GET  /api/conversations/search?q=          → recherche parmi les conversations
 *   POST /api/conversations                    → créer ou récupérer une conversation
 *   GET  /api/conversations/{id}/messages      → historique des messages
 *   POST /api/conversations/{id}/read          → marquer comme lus
 *
 * L'envoi de messages en temps réel passe par STOMP (ChatWebSocketController).
 *//*

@RestController
@RequestMapping("/conversations")
class ChatController(
    private val chatQueryService: ChatQueryService,
    private val chatCommandService: ChatCommandService,
    private val userClient: UserClient,
) {

    @GetMapping
    fun getConversations(): ResponseEntity<List<ConversationResponse>> {
        val currentUser = userClient.getCurrentUser()
        return ResponseEntity.ok(chatQueryService.getConversations(currentUser.id))
    }

    @GetMapping("/search")
    fun searchConversations(@RequestParam q: String): ResponseEntity<List<ConversationResponse>> {
        val currentUser = userClient.getCurrentUser()
        return ResponseEntity.ok(chatQueryService.searchConversations(currentUser.id, q))
    }

    @PostMapping
    fun getOrCreateConversation(
        @RequestBody request: CreateConversationRequest,
    ): ResponseEntity<Map<String, UUID>> {
        val currentUser = userClient.getCurrentUser()
        val conversationId = chatCommandService.getOrCreateConversation(currentUser.id, request.participantId)
        return ResponseEntity.ok(mapOf("conversationId" to conversationId))
    }

    @GetMapping("/{conversationId}/messages")
    fun getMessages(@PathVariable conversationId: UUID): ResponseEntity<List<MessageResponse>> {
        val currentUser = userClient.getCurrentUser()
        return ResponseEntity.ok(chatQueryService.getMessages(conversationId, currentUser.id))
    }

    @PostMapping("/{conversationId}/read")
    fun markAsRead(@PathVariable conversationId: UUID): ResponseEntity<Void> {
        val currentUser = userClient.getCurrentUser()
        chatCommandService.markAsRead(conversationId, currentUser.id)
        return ResponseEntity.noContent().build()
    }
}*/
