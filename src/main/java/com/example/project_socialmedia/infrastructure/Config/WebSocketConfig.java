package com.example.project_socialmedia.infrastructure.Config;


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
        // Định nghĩa prefix cho các destination mà client có thể subscribe
        config.enableSimpleBroker("/topic");
        // Định nghĩa prefix cho các message mà client gửi đến server
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Định nghĩa endpoint mà client sẽ kết nối đến
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000").withSockJS();
    }
/*
   '/ws': Là endpoint mà client (React) sẽ kết nối đến.
    setAllowedOrigins("http://localhost:3000"): Cho phép CORS từ React app chạy trên localhost:3000.
    Điều chỉnh nếu frontend chạy trên cổng khác.
    withSockJS(): Sử dụng SockJS để hỗ trợ các trình duyệt không hỗ trợ WebSocket thuần.
 */
}
