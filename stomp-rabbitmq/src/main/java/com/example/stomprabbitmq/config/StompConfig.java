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

  /*@Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    // 메시지채널에서 주고받는 메시지를 확인 및/또는 수정할 수 있는 인터셉터 추가
    registration.interceptors(new ChannelInterceptor() {
      // 메시지가 실제로 채널로 전송되기 전에 호출됨
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        log.info("headerAccessor : {}", headerAccessor);

        // 헤더에서 유저명을 가져온다.
        List<String> usernames = Optional
            .ofNullable(headerAccessor.getNativeHeader("username"))
            .orElseGet(Collections::emptyList);
        log.info("usernames : {}", usernames);

        // 처음 접속 시도시 유저 데이터를 심어준다.
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand()) && !usernames.isEmpty()) {
          log.info("username : {}", new SimpleUsernamePrincipal(usernames.get(0)));
          headerAccessor.setUser(new SimpleUsernamePrincipal(usernames.get(0)));
          sessionKeys.put(usernames.get(0), headerAccessor.getSessionId());
        }

        // 사용자 접속 해제시 사용자 큐를 삭제한다.
        if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
          String sessionKey = sessionKeys.get(headerAccessor.getUser().getName());
          // message-user는 생성되는 큐의 접두사
          // /users/queue/message의 message를 가져와 -user를 붙여만든다.
          new RabbitAdmin(rabbitTemplate).deleteQueue("message-user" + sessionKey);
          sessionKeys.remove(sessionKey);
        }

        return message;
      }
    });*/
}
