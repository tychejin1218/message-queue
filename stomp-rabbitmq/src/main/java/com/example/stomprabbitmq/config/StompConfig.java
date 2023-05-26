package com.example.stomprabbitmq.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
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
    registry.setApplicationDestinationPrefixes("/pub") // 메시지 발행(publish)할 경로를 설정
        .enableStompBrokerRelay("/queue", "/topic", "/exchange") // 메시지 구독(subscribe)할 경로를 설정
        // RabbitMQ 브로커와 연결을 위한 호스트, 가상 호스트 및 포트, 관리자 로그인 설정
        // RabbitMQ 관리자(localhost:15672) > Connections 탭에서 연결 확인할 수 있음
        .setRelayHost("localhost")
        .setVirtualHost("/dev")
        .setRelayPort(61613)
        .setSystemLogin("guest")
        .setSystemPasscode("guest")
        .setClientLogin("guest")
        .setClientPasscode("guest");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    // 메시지 채널에서 주고받는 메시지를 확인 및 수정할 수 있는 인터셉터 추가
    registration.interceptors(new ChannelInterceptor() {
      // 메시지가 채널로 전송되기 전에 호출
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);
        log.info("stompHeaderAccessor : {}", stompHeaderAccessor);

        return message;
      }
    });
  }
}
