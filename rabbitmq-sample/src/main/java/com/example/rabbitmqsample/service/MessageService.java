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

  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  private final RabbitTemplate rabbitTemplate;

  /**
   * RabbitMQ로 메시지를 전송하는 메서드
   *
   * @param messageDto 전송할 메시지의 DTO 객체
   */
  public void sendMessage(MessageDto messageDto) {
    log.info("message sent: {}", messageDto.toString());
    rabbitTemplate.convertAndSend(exchangeName, "sample.queue", messageDto);
  }

  /**
   * 메시지를 수신하는 RabbitMQ 리스너
   *
   * @param messageDto 수신한 메시지를 담고 있는 MessageDto 객체
   */
  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consume(MessageDto messageDto) {
    log.info(String.format("Received message: {}", messageDto.toString()));
  }
}
