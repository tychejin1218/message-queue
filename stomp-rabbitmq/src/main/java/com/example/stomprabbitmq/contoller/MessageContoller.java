package com.example.stomprabbitmq.contoller;

import com.example.stomprabbitmq.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageContoller {

  private final ConnectionFactory connectionFactory;
  private final RabbitAdmin rabbitAdmin;
  private final RabbitTemplate rabbitTemplate;
  private final SimpMessagingTemplate simpMessagingTemplate;

  /*@RequestMapping(value = "/send/message/test01", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios01(@RequestBody UserDto userDto) {

    String queueName = userDto.getTeacherId();

    if (!isQueueExists(queueName)) {
      createQueue(queueName);
    }

    rabbitTemplate.convertAndSend(queueName, userDto,
        message -> {
          MessageProperties messageProperties = message.getMessageProperties();
          messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT); // 장애 상황에서도 손실되지 않도록 설정
          messageProperties.setExpiration("5000"); // 5000ms(5초)로 설정
          messageProperties.setContentType("application/json"); // JSON 형식
          return new Message(message.getBody(), messageProperties);
        }
    );
    //simpMessagingTemplate.convertAndSend("/queue/" + queueName, userDto);
  }*/

  @RequestMapping(value = "/send/message/test01", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios01(@RequestBody UserDto userDto) {

    Map headers = Map.of(
        "x-expires", 1000 * 60 * 60, // Queue가 사용되지 않은 상태(Consumer가 없을 때) 유지되는 시간이며, 초과 시 자동으로 삭제
        "x-message-ttl", 1000 * 60 * 10, // Queue에 전송된 메시지가 유지되는 시간이며, 초과 시 자동으로 삭제(큐 단위로 설정)
        "x-overflow", "drop-head", // 가장 오래된 메시지를 삭제하고, 새로운 메시지를 저장
        "x-queue-name", "teacher_" + userDto.getTeacherId()
    );

    /*MessagePostProcessor messageHeaders = message -> {
      SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
      accessor.setHeader("message-id", "123");
      accessor.setHeader("message-user", "456");
      return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    };

    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(), userDto, headers,
        messageHeaders);*/

    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(), userDto, headers);
  }

  @RequestMapping(value = "/send/message/test02", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios02(@RequestBody UserDto userDto) {
//    Map map = Map.of("x-queue-name", "teacher" + userDto.getTeacherId());
//    simpMessagingTemplate.convertAndSend("/topic/" + userDto.getTeacherId(), userDto, map);

    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(), userDto);
  }

  /***
   * 큐(Queue) 존재 여부 확인
   *
   * @param queueName 큐(Queue)명
   * @return 큐 존재 여부
   */
  public boolean isQueueExists(String queueName) {
    return rabbitAdmin.getQueueProperties(queueName) != null;
  }

  /**
   * 큐(Queue)를 생성
   *
   * @param queueName 설명
   */
  public void createQueue(String queueName) {
    Queue queue = new Queue(queueName);
    rabbitAdmin.declareQueue(queue);
  }
}
