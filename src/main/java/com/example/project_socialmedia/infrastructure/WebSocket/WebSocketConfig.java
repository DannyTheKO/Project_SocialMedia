package com.example.project_socialmedia.infrastructure.WebSocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // prefix for destination that client can subscribe
        config.enableSimpleBroker("/topic");
        // prefix for message that client send to server
        config.setApplicationDestinationPrefixes("/app");
        // prefix for default destination if controller return to void
        // and provide send message to specific user ( use with SimpMessagingTemplate )
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint for client connect
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000").withSockJS();
    }
/*
   '/ws': Là endpoint mà client (gui React) kết nối đến
 */
}
