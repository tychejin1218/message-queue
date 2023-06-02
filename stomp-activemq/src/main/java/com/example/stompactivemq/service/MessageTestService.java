package com.example.stompactivemq.service;

import com.example.stompactivemq.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageTestService {

  private final JmsTemplate jmsTemplate;
  private final String queueName = "test-queue";

  public void sendMessageTestQueue(MessageDto messageDto) {
    jmsTemplate.convertAndSend(queueName, messageDto.getMessage());
  }

  @JmsListener(destination = queueName)
  public void receiveMessage(String message) {
    log.info("Received message: {}", message);
  }

  @JmsListener(destination = queueName)
  @SendTo("greet-queue")
  public String receiveMessageAndReply(String message) {
    log.info("Received message: {}", message);
    return "Hello " + message;
  }

  @JmsListener(destination = queueName)
  public void receiveGreeting(String message) {
    log.info("Received greeting: {}", message);
  }
}
