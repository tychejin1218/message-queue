package com.example.activemq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // WebSocket 메시지 브로커를 활성화
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${spring.activemq.stomp.host}")
  private String activemqStompHost;

  @Value("${spring.activemq.stomp.port}")
  private String activemqStompPort;

  @Value("${spring.activemq.user}")
  private String activemqUsername;

  @Value("${spring.activemq.password}")
  private String activemqPassword;

  /**
   * STOMP 관련 설정을 구성
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws") // Client에서 WebSocker 연결할 엔드포인트를 설정
        .setAllowedOriginPatterns("*")
        .withSockJS(); // withSockJS()를 사용하여 SockJS를 활성화
  }

  /**
   * 메시지 브로커 관련 설정을 구성
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableStompBrokerRelay("/queue", "/topic", "/exchange") // 메시지 구독(subscribe)할 경로를 설정
        // ActiveMQ 브로커와 연결을 위한 호스트, 가상 호스트 및 포트, 관리자 로그인 설정
        .setRelayHost(activemqStompHost)
        .setRelayPort(Integer.parseInt(activemqStompPort))
        .setSystemLogin(activemqUsername)
        .setSystemPasscode(activemqPassword)
        .setClientLogin(activemqUsername)
        .setClientPasscode(activemqPassword);
  }
}

