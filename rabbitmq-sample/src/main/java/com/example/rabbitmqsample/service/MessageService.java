package com.example.rabbitmqsample.service;

import com.example.rabbitmqsample.dto.MessageDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

  @Value("${rabbitmq.exchange.name}")
  private String exchangeName;

  private final RabbitTemplate rabbitTemplate;

  /**
   * Queue로 메시지를 발행
   *
   * @param messageDto 발행할 메시지의 DTO 객체
   */
  public void sendMessage(MessageDto messageDto) {
    log.info("message sent: {}", messageDto.toString());
    rabbitTemplate.convertAndSend(exchangeName, "sample.queue", messageDto);
  }

  /**
   * Queue에서 메시지를 구독
   *
   * @param messageDto 구독한 메시지를 담고 있는 MessageDto 객체
   */
  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consume(MessageDto messageDto) {
    log.info(String.format("Received message: {}", messageDto.toString()));
  }
}
