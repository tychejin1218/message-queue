package com.example.activemq.service;

import com.example.activemq.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageQueueService {

  @Value("${activemq.queue.name}")
  private String queueName;

  private final JmsTemplate jmsTemplateQueue;

  /**
   * Queue로 메시지를 발행
   *
   * @param messageDto 발행할 메시지의 DTO 객체
   */
  public void sendMessageQueue(MessageDto messageDto) {
    log.info("Message sent to queue: {}", messageDto.toString());
    jmsTemplateQueue.convertAndSend(queueName, messageDto);
  }

  /**
   * Queue에서 메시지를 구독
   *
   * @param messageDto 구독한 메시지를 담고 있는 MessageDto 객체
   */
  @JmsListener(destination = "${activemq.queue.name}", containerFactory = "containerFactoryQueue")
  public void reciveMessageQueue(MessageDto messageDto) {
    log.info("Received message from queue: {}", messageDto.toString());
  }
}
