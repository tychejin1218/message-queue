package com.example.rabbitmq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    // Client에서 WebSocker 연결할 때 사용할 API 경로 설정
    registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {

    // 메시지를 보낼 때 경로 설정
    registry.setApplicationDestinationPrefixes("/pub");

    // 메시지를 받을 때 경로 설정
    registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
        .setRelayHost("localhost")
        .setVirtualHost("/dev")
        .setRelayPort(61613)
        .setClientLogin("guest")
        .setClientPasscode("guest");
  }
}
