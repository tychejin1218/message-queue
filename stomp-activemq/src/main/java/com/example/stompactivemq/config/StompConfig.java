package com.example.stompactivemq.config;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import javax.management.ObjectName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.tcp.TcpOperations;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class StompConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${activemq.openwire.url}")
  private String openWireUrl;

  @Value("${activemq.openwire.port}")
  private String openWirePort;

  @Value("${activemq.stomp.url}")
  private String stompUrl;

  @Value("${activemq.stomp.port}")
  private String stompPort;

  @Value("${activemq.user}")
  private String user;

  @Value("${activemq.password}")
  private String password;

  /**
   * STOMP 관련 설정을 구성
   *
   * @param registry
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/message/subscribe") // Client에서 WebSocker 연결할 엔드포인트를 설정
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
        // ActiveMQ 브로커와 연결을 위한 호스트, 가상 호스트 및 포트, 관리자 로그인 설정
        .setRelayHost(openWireUrl)
        .setRelayPort(Integer.parseInt(openWirePort))
        .setSystemLogin(user)
        .setSystemPasscode(password)
        .setClientLogin(user)
        .setClientPasscode(password)
        .setTcpClient(createClient());
  }

  private TcpOperations<byte[]> createClient() {
    return new ReactorNettyTcpClient<>((client) -> client
        .addressSupplier(this::getAddress)
        .secure(), new StompReactorNettyCodec());
  }

  private SocketAddress getAddress() {
    try {
      InetAddress address = InetAddress.getByName(stompUrl);
      SocketAddress socketAddress = new InetSocketAddress(address, Integer.parseInt(stompPort));
      return socketAddress;
    } catch (Exception e) {
      log.error("getAddress", e);
    }
    return null;
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