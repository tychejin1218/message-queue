package com.example.stomprabbitmq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class StompConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * STOMP 관련 설정을 구성
   *
   * @param registry
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws") // Client에서 WebSocker 연결할 엔드포인트를 설정
        .setAllowedOriginPatterns("*")
        .withSockJS(); // withSockJS()를 사용하여 SockJS를 활성화
  }

  /**
   * 메시지 브로커 관련 설정을 구성
   *
   * @param registry
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableStompBrokerRelay("/queue", "/topic", "/exchange") // 메시지 구독(subscribe)할 경로를 설정
        // RabbitMQ 브로커와 연결을 위한 호스트, 가상 호스트 및 포트, 관리자 로그인 설정
        // RabbitMQ 관리자(localhost:15672) > Connections 탭에서 연결 확인할 수 있음
        .setRelayHost("b-f142d8f9-4d6e-4736-9a78-1bf6cd395c86-1.mq.ap-northeast-2.amazonaws.com")
        .setVirtualHost("/")
        .setRelayPort(61614)
//        .setSystemLogin("guest")
//        .setSystemPasscode("guest")
        .setClientLogin("pan_admin")
        .setClientPasscode("dkpan@#2023!$");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(stompHeaderAccessor.getCommand())) {
          log.info("configureClientInboundChannel Connect...");
        }

        if (StompCommand.SUBSCRIBE.equals(stompHeaderAccessor.getCommand())) {
          log.info("configureClientInboundChannel Subscribe...");
        }

        return message;
      }
    });
  }
}
