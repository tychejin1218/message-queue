package com.example.stomprabbitmq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class StompConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    // Client에서 WebSocker 연결할 때 사용할 API 경로 설정
    registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {

    // RabbitMQ 관리자(localhost:15672) > Connections 탭에서 연결 확인할 수 있음
    registry.setApplicationDestinationPrefixes("/pub") // 메세지를 보낼(publish) 경로를 설정
        .enableStompBrokerRelay("/queue", "/topic",
            "/exchange") // 메세지 수신(Subscribe), 경로를 설정해주는 메서드
        .setRelayHost("localhost")
        .setVirtualHost("/dev")
        .setRelayPort(61613) // RabbitMQ STOMP 기본 포트
        .setSystemLogin("guest")
        .setSystemPasscode("guest")
        .setClientLogin("guest")
        .setClientPasscode("guest");
  }
}
