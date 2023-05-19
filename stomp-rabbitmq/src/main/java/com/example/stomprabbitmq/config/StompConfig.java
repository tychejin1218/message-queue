package com.example.stomprabbitmq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class StompConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    // Client에서 WebSocker 연결할 때 사용할 API 경로 설정
    registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")
        //.setHandshakeHandler(new CustomHandshakeHandler())
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {

    // 메시지를 보낼 때 경로 설정
    registry.setApplicationDestinationPrefixes("/pub");

    // 메시지를 받을 때 경로 설정
    // RabbitMQ 관리자(localhost:15672) > Connections 탭에서 연결 확인할 수 있음
    registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
        .setAutoStartup(true)
        .setRelayHost("localhost")
        .setVirtualHost("/dev")
        .setRelayPort(61613)
        .setClientLogin("guest")
        .setClientPasscode("guest");
  }
}
