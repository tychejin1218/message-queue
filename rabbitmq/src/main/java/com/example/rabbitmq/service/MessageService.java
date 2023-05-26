package com.example.rabbitmq.service;

import com.example.rabbitmq.dto.KeyValueMessage;
import com.example.rabbitmq.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.routing.stk.key}")
  private String routingKeyStk;

  @Value("${rabbitmq.routing.ste.key}")
  private String routingKeySte;

  private final RabbitTemplate rabbitTemplate;

  public void sendMessage01(UserDto userDto) {
    log.info("message : {}", userDto.toString());
    if ("STK".equals(userDto.getSubjectCode())) {

      rabbitTemplate.convertAndSend(exchange, routingKeyStk, userDto);
    } else if ("STE".equals(userDto.getSubjectCode())) {
      rabbitTemplate.convertAndSend(exchange, routingKeySte, userDto);
    }
  }

  public void sendMessage(UserDto userDto) {
    log.info("message : {}", userDto.toString());
    if ("STK".equals(userDto.getSubjectCode())) {

      // 제한 시간
      MessageProperties messageProperties = new MessageProperties();
      messageProperties.setExpiration(String.valueOf(1000 * 60));
      Message message = new Message(userDto.toString().getBytes(), messageProperties);
      rabbitTemplate.convertAndSend(exchange, routingKeyStk, message);

    } else if ("STE".equals(userDto.getSubjectCode())) {
      rabbitTemplate.convertAndSend(exchange, routingKeySte, userDto);
    }
  }
}
