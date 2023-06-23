package com.example.activemq.config;

import com.example.activemq.common.stomp.StompErrorHandler;
import com.example.activemq.common.stomp.StompPreHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // WebSocket 메시지 브로커를 활성화
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final StompPreHandler stompPreHandler;
  private final StompErrorHandler stompErrorHandler;

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
    registry.addEndpoint("/ws") // WebSocket 엔드포인트 설정
        .setAllowedOriginPatterns("*")
        .withSockJS(); // withSockJS()를 사용하여 SockJS를 활성화
    registry.setErrorHandler(stompErrorHandler);
  }

  /**
   * 메시지 브로커 관련 설정을 구성
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableStompBrokerRelay("/queue") // 메시지를 구독할 경로를 설정
        // ActiveMQ 브로커와 연결을 위한 호스트, 가상 호스트 및 포트, 관리자 로그인 설정
        .setRelayHost(activemqStompHost)
        .setRelayPort(Integer.parseInt(activemqStompPort))
        .setSystemLogin(activemqUsername)
        .setSystemPasscode(activemqPassword)
        .setClientLogin(activemqUsername)
        .setClientPasscode(activemqPassword);
  }

  /**
   * 메시지 요청/응답에 관련된 인터셉터를 추가
   */
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(stompPreHandler);
  }
}

