package dev.jefy.connectpro.messaging.infrastructure

import dev.jefy.connectpro.user.infrastructure.config.JwtService
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

/**
 * @author Jôph Yamba
 *
 * Architecture STOMP :
 *   - Endpoint WS  : /ws
 *   - Broker topics : /topic (broadcast), /queue (user-specific)
 *   - Prefix app   : /app  (messages vers les controllers)
 *   - Prefix user  : /user (messages ciblés via SimpMessagingTemplate)
 *
 * Flux typique :
 *   Flutter → /app/chat.send → ChatWebSocketController → broker → /user/{id}/queue/messages
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
) : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry
            .addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
        // SockJS non nécessaire pour Flutter native, mais utile pour le web
    }

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic", "/queue")
        config.setApplicationDestinationPrefixes("/app")
        config.setUserDestinationPrefix("/user")
    }

    // Intercepteur JWT sur le handshake STOMP CONNECT
    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
                val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
                if (accessor?.command == StompCommand.CONNECT) {
                    val authHeader = accessor.getFirstNativeHeader("Authorization")
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        val token = authHeader.substring(7)
                        val username = jwtService.extractUsername(token)

                        if (username != null && SecurityContextHolder.getContext().authentication == null) {
                            val userDetails = userDetailsService.loadUserByUsername(username)

                            if (jwtService.isTokenValid(token, userDetails)) {
                                val authToken = UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.authorities
                                )
                                SecurityContextHolder.getContext().authentication = authToken
                                accessor.user = authToken
                            }
                        }
                    }
                }
                return message
            }
        })
    }
}