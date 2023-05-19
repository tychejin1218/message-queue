package com.example.stomprabbitmq.contoller;

import com.example.stomprabbitmq.dto.UserDto;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageContoller {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final AmqpAdmin amqpAdmin;
  private final SimpMessageSendingOperations simpMessageSendingOperations;

  /**
   * 토픽은 일대다(One-to-Many) 통신에 사용
   *
   * @param userDto 설명
   */
  @MessageMapping("/send/topic")
  public void sendMessageTopic(
      @Payload UserDto userDto) {
    simpMessageSendingOperations.convertAndSend("/topic/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  /**
   * 큐는 일대일(One-to-One) 통신에 사용
   *
   * @param userDto 설명
   */
  @MessageMapping("/send/queue")
  public void sendMessageQueue(
      @Payload UserDto userDto) {
    simpMessageSendingOperations.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  @MessageMapping("/send/message")
  public void sendMessage(
      @Payload UserDto userDto) {
    simpMessageSendingOperations.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  @MessageMapping("/send/message1")
  public void sendMessage1(
      @Payload UserDto userDto) {

    String queueName = userDto.getTeacherId();

    Queue queue = new Queue(queueName, false, false, false);
    amqpAdmin.declareQueue(queue);

    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  @RequestMapping(value = "/send/rest/message", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios(@RequestBody UserDto userDto) {
    simpMessageSendingOperations.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }
}
