package com.example.stompactivemq.service;

import com.example.stompactivemq.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

  private final JmsTemplate jmsTemplate;

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   */
  public void sendMessage(MessageDto messageDto) {
    String queueName = messageDto.getTeacherId();
    jmsTemplate.convertAndSend(queueName, messageDto.getMessage());
  }
}
